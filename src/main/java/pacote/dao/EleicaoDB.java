package pacote.dao;

import com.mongodb.client.MongoDatabase;
import pacote.bean.CargoBean;
import pacote.bean.EleicaoBean;
import pacote.config.ConfigStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Filters.*;
import pacote.config.ConfigStatus;

public class EleicaoDB extends ConexaoMongo {

	public boolean cadastrar(EleicaoBean campos) {
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_eleicao);
			if(colection != null) {
				System.out.println(campos.getDataInicio());
				Document documento = new Document();
				documento.put("nome", campos.nome );
				documento.put("descricao", campos.descricao );
				documento.put("status", campos.status );
				documento.put("dt_inicio", campos.dataInicio );
				documento.put("dt_fim", campos.dataFim );
				documento.put("dt_criacao", new Date());
				documento.put("cargos", null);
				colection.insertOne(documento);
			}
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean atualizar(EleicaoBean campos) {
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_eleicao);
			if(colection != null) {
				Document documento = new Document();
				documento.put("nome", campos.nome );
				documento.put("descricao", campos.descricao );
				documento.put("status", campos.status );
				documento.put("status", campos.status );
				documento.put("dt_inicio", campos.dataInicio );
				documento.put("dt_fim", campos.dataFim );
				documento.put("dt_criacao", new Date());
				colection.updateOne(eq("_id", new ObjectId(campos.id)), new Document("$set", documento));
			}
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean adicionarCargo(EleicaoBean campos) {
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_eleicao);
			List<String>  idsCargo = new ArrayList<String>();
			for(CargoBean c : campos.getCargos()) {
				idsCargo.add(c.id);
			}
			if(colection != null) {
				Document documento = new Document();
				documento.put("cargos", idsCargo);
				colection.updateOne(eq("_id", new ObjectId(campos.id)), new Document("$set", documento));
			}
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean atualizarStatus(EleicaoBean campos) {
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_eleicao);
			List<String>  idsCargo = new ArrayList<String>();
			
			if(colection != null) {
				Document documento = new Document();
				documento.put("status", campos.getStatus());
				colection.updateOne(eq("_id", new ObjectId(campos.getId())), new Document("$set", documento));
			}
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean excluir(EleicaoBean campos) {
		ConexaoMongo conn = new ConexaoMongo();
		try {
			
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_eleicao);
			if(colection != null) {
				Document documento = new Document();
				documento.put("_id",  new ObjectId(campos.id) );
				colection.deleteOne(documento);
			}
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public List<EleicaoBean> listarEleicoes(){
		ArrayList<EleicaoBean> lista = null;
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_eleicao);
			if(colection != null) {
				MongoCursor<Document> cursor = colection.find().iterator();
				try {
					lista = new ArrayList<EleicaoBean>();
					while (cursor.hasNext()) {
						EleicaoBean elemento = new EleicaoBean();
				        Document doc = cursor.next();
				        elemento.id = doc.get("_id").toString();
				        elemento.nome = doc.get("nome", "").toString();
				        elemento.descricao = doc.get("descricao", "").toString();
				        elemento.status = doc.get("status", "0").toString();
				        if(elemento.status.equals(ConfigStatus.ATIVO.valor())) {
				        	elemento.statusDescricao = ConfigStatus.DESCRICAO_ATIVO.valor();
						}else if(elemento.status.equals(ConfigStatus.INATIVO.valor())) {
							elemento.statusDescricao = ConfigStatus.DESCRICAO_INATIVO.valor();
						}
				        
				        elemento.dataInicio = doc.getDate("dt_inicio");
				        elemento.dataFim = doc.getDate("dt_fim");
				        
				        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");     
				        elemento.dataInicioDescricao = df.format(elemento.dataInicio);
				        elemento.dataFimDescricao = df.format(elemento.dataFim);
				        elemento.cargos = new ArrayList<CargoBean>();
				        
				        try {
				        	elemento.cargosSelected = (List<String>) doc.get("cargos");
				        	for(String it : elemento.getCargosSelected()) {
				        		elemento.getCargos().add(new CargoDB().getCargo(it));
				        	}
				        }catch(Exception exx) {
				        	exx.printStackTrace();
				        }
				        
				        lista.add(elemento);
				    }
				} catch(Exception exx){
					exx.printStackTrace();
				}finally {
				    cursor.close();
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<EleicaoBean> listarEleicoesAtivasNaoIniciadas(){
		ArrayList<EleicaoBean> lista = null;
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_eleicao);
			if(colection != null) {
				MongoCursor<Document> cursor = colection.find(and(eq("status", ConfigStatus.ATIVO.valor()), gt("dt_inicio", new Date()))).iterator();
				try {
					lista = new ArrayList<EleicaoBean>();
					while (cursor.hasNext()) {
						EleicaoBean elemento = new EleicaoBean();
				        Document doc = cursor.next();
				        elemento.id = doc.get("_id").toString();
				        elemento.nome = doc.get("nome", "").toString();
				        elemento.descricao = doc.get("descricao", "").toString();
				        elemento.status = doc.get("status", "0").toString();
				        if(elemento.status.equals(ConfigStatus.ATIVO.valor())) {
				        	elemento.statusDescricao = ConfigStatus.DESCRICAO_ATIVO.valor();
						}else if(elemento.status.equals(ConfigStatus.INATIVO.valor())) {
							elemento.statusDescricao = ConfigStatus.DESCRICAO_INATIVO.valor();
						}
				        
				        elemento.dataInicio = doc.getDate("dt_inicio");
				        elemento.dataFim = doc.getDate("dt_fim");
				        
				        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");     
				        elemento.dataInicioDescricao = df.format(elemento.dataInicio);
				        elemento.dataFimDescricao = df.format(elemento.dataFim);
				        elemento.cargos = new ArrayList<CargoBean>();
				        
				        try {
				        	elemento.cargosSelected = (List<String>) doc.get("cargos");
				        	for(String it : elemento.getCargosSelected()) {
				        		elemento.getCargos().add(new CargoDB().getCargo(it));
				        	}
				        }catch(Exception exx) {
				        	exx.printStackTrace();
				        }
				        
				        lista.add(elemento);
				    }
				} catch(Exception exx){
					exx.printStackTrace();
				}finally {
				    cursor.close();
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return lista;
	}
}
