package pacote.dao;

import com.mongodb.client.MongoDatabase;
import pacote.bean.CargoBean;
import pacote.config.ConfigStatus;
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

public class CargoDB extends ConexaoMongo {

	public boolean cadastrar(CargoBean campos) {
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_cargo);
			if(colection != null) {
				Document documento = new Document();
				documento.put("nome", campos.nome );
				documento.put("descricao", campos.descricao );
				documento.put("status", campos.status );
				documento.put("dt_criacao", new Date());
				colection.insertOne(documento);
			}
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean atualizar(CargoBean campos) {
		ConexaoMongo conn = new ConexaoMongo();
		try {
			
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_cargo);
			if(colection != null) {
				Document documento = new Document();
				documento.put("nome", campos.nome );
				documento.put("descricao", campos.descricao );
				documento.put("status", campos.status );
				colection.replaceOne(eq("_id", new ObjectId(campos.id)), documento);			
			}
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean excluir(CargoBean campos) {
		ConexaoMongo conn = new ConexaoMongo();
		try {
			
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_cargo);
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
	
	public List<CargoBean>  listarCargos(){
		ArrayList<CargoBean> lista = null;
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_cargo);
			if(colection != null) {
				MongoCursor<Document> cursor = colection.find().iterator();
				try {
					lista = new ArrayList<CargoBean>();
					while (cursor.hasNext()) {
				    	CargoBean elemento = new CargoBean();
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
				        lista.add(elemento);
				    }
				} finally {
				    cursor.close();
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return lista;
	}
	
	public List<CargoBean>  listarCargosAtivos(){
		ArrayList<CargoBean> lista = null;
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_cargo);
			if(colection != null) {
				MongoCursor<Document> cursor = colection.find(eq("status", ConfigStatus.ATIVO.valor())).iterator();
				try {
					lista = new ArrayList<CargoBean>();
					while (cursor.hasNext()) {
				    	CargoBean elemento = new CargoBean();
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
				        lista.add(elemento);
				    }
				} finally {
				    cursor.close();
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return lista;
	}
	
	public CargoBean  getCargo(String id){
		CargoBean c = null;
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_cargo);
			if(colection != null) {
				MongoCursor<Document> cursor = colection.find(eq("_id", new ObjectId(id))).iterator();
				try {
					while (cursor.hasNext()) {
						if(c != null) {
							break;
				        }
						c = new CargoBean();
				    	Document doc = cursor.next();
				        c.id = doc.get("_id").toString();
				        c.nome = doc.get("nome", "").toString();
				        c.descricao = doc.get("descricao", "").toString();
				        c.status = doc.get("status", "0").toString();
				        if(c.status.equals(ConfigStatus.ATIVO.valor())) {
				        	c.statusDescricao = ConfigStatus.DESCRICAO_ATIVO.valor();
						}else if(c.status.equals(ConfigStatus.INATIVO.valor())) {
							c.statusDescricao = ConfigStatus.DESCRICAO_INATIVO.valor();
						}
				    }
				} finally {
				    cursor.close();
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return c;
	}
}
