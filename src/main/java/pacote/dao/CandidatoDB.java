package pacote.dao;

import com.mongodb.client.MongoDatabase;

import pacote.bean.CandidatoBean;
import pacote.bean.CargoBean;
import pacote.bean.EleicaoBean;
import pacote.config.ConfigStatus;

import java.io.File;
import java.io.InputStream;
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

public class CandidatoDB extends ConexaoMongo {
	public boolean cadastrar(CandidatoBean campos) {
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_candidato);
			if(colection != null) {
				Document documento = new Document();
				documento.put("nome", campos.getNome() );
				documento.put("sobrenome", campos.getSobrenome() );
				documento.put("dt_nascimento", campos.dataNascimento );
				documento.put("genero", campos.getGenero() );
				documento.put("dt_criacao", new Date());
				documento.put("cpf", campos.getCpf());				
				colection.insertOne(documento);
			}
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean atualizar(CandidatoBean campos) {
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_candidato);
			if(colection != null) {
				Document documento = new Document();
				documento.put("nome", campos.getNome() );
				documento.put("sobrenome", campos.getSobrenome() );
				documento.put("dt_nascimento", campos.dataNascimento );
				documento.put("genero", campos.getGenero() );
				documento.put("dt_criacao", new Date());
				documento.put("cpf", campos.getCpf());				
				//colection.insertOne(documento);
				colection.updateOne(eq("_id", new ObjectId(campos.getId())), new Document("$set", documento));
			}
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean checarDuplicidadeCPF(CandidatoBean campos) {
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_candidato);
			MongoCursor<Document> cursor  = null;
			if(colection != null) {
				if(campos.getId() != null) {
					cursor = colection.find(and(eq("cpf", campos.getCpf()), ne("_id", new ObjectId(campos.getId()) ) )).iterator();
				}else {
					cursor = colection.find(eq("cpf", campos.getCpf()) ).iterator();
				}
				
				while (cursor.hasNext()) {
					return false;
				}
			}
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public List<CandidatoBean> listarCandidatos(){
		ArrayList<CandidatoBean> lista = new ArrayList<CandidatoBean>();
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_candidato);
			if(colection != null) {
				MongoCursor<Document> cursor = colection.find().iterator();
				while (cursor.hasNext()) {
			    	CandidatoBean elemento = new CandidatoBean();
			        Document doc = cursor.next();
			        elemento.id = doc.get("_id").toString();
			        elemento.nome = doc.get("nome", "").toString();
			        elemento.sobrenome = doc.get("sobrenome", "").toString();
			        elemento.dataNascimento = doc.getDate("dt_nascimento");
			        elemento.setGenero(doc.getString("genero"));
			        elemento.cpf = doc.getString("cpf");
			      
			        lista.add(elemento);
			    }
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			
		}
		return lista;
	}
	
	public CandidatoBean getCandidato(String id) {
		CandidatoBean c = null;
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_candidato);
			if(colection != null) {
				MongoCursor<Document> cursor = colection.find(eq("_id", new ObjectId(id))).iterator();
				while (cursor.hasNext()) {
			    	CandidatoBean elemento = new CandidatoBean();
			        Document doc = cursor.next();
			        elemento.id = doc.get("_id").toString();
			        elemento.nome = doc.get("nome", "").toString();
			        elemento.sobrenome = doc.get("sobrenome", "").toString();
			        elemento.dataNascimento = doc.getDate("dt_nascimento");
			        elemento.setGenero(doc.getString("genero"));
			        elemento.cpf = doc.getString("cpf");
			        c = elemento;
			        break;
			        
			    }
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return c;
	}
	
	public boolean excluirCandidato(String id) {
		boolean c = false;
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_candidato);
			if(colection != null) {
				colection.deleteOne(eq("_id", new ObjectId(id)));
				c = true;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return c;
	}
	
	public boolean subirFoto(InputStream streamToUploadFrom, CandidatoBean candidato, String extensao, String mime) {
		boolean retorno = false;
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			FileManagerMongo fdb = new FileManagerMongo(db);
			retorno = fdb.salvarFotoCandidato(streamToUploadFrom, candidato, extensao, mime);
			if(retorno == false) {
				throw new Exception("Erro ao salvar arquivo");
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
	
	public InputStream getFotoCandidatoDownload(CandidatoBean candidato) {
		InputStream in = null;
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			FileManagerMongo fdb = new FileManagerMongo(db);
			in = fdb.getFotoCandidatoDownload(candidato);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return in;
	}
	
	public String getTipoFotoCandidato(CandidatoBean candidato) {
		String retorno = null;
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			FileManagerMongo fdb = new FileManagerMongo(db);
			retorno = fdb.getTipoFoto(candidato);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
}
