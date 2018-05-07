package pacote.dao;

import com.mongodb.client.MongoDatabase;
import pacote.bean.CargoBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Filters.*;

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
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_cargo);
			if(colection != null) {
				Document documento = new Document();
				documento.put("nome", campos.nome );
				documento.put("descricao", campos.descricao );
				documento.put("status", campos.status );
				//colection.updateOne(eq("_id", campos.id), documento);
				
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
				//MongoCursor<Document> cursor = colection.find(fields(include("nome", "descricao", "status"))).iterator();
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
}
