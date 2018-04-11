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
	
	public List<CargoBean>  listarCargos(){
		List<CargoBean> lista = null;
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_cargo);
			if(colection != null) {
				//MongoCursor<Document> cursor = colection.find(fields(include("nome", "descricao", "status"))).iterator();
				MongoCursor<Document> cursor = colection.find().iterator();
				try {
					System.out.println("Tentando fazer a busca de registros");
				    while (cursor.hasNext()) {
				    	CargoBean elemento = new CargoBean();
				    	System.out.println("Resultado da busca");
				        System.out.println(cursor.next().toJson());
				        Document doc = cursor.next();
				        /*doc.toJson()
				        elemento.id = campos.get(0).toString();
				        elemento.nome = campos.get(1).toString();
				        elemento.descricao = campos.get(2).toString();
				        elemento.status = campos.get(3).toString();*/
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
