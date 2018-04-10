package pacote.dao;

import com.mongodb.client.MongoDatabase;
import pacote.bean.CargoBean;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

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
			
			BasicDBObject allQuery = new BasicDBObject();
			BasicDBObject fields = new BasicDBObject();
			
			MongoCursor<Document> cursor = colection.find().iterator();
			try {
			    while (cursor.hasNext()) {
			        System.out.println(cursor.next().toJson());
			        
			    }
			} finally {
			    cursor.close();
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return lista;
	}
	
}
