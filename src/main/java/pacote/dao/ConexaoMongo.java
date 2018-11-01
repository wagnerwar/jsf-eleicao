package pacote.dao;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.MongoClient; 
import com.mongodb.client.MongoCollection; 

public class ConexaoMongo {
	public static String db = "eleicao";
	public static String cl_cargo = "cargo";
	public static String cl_eleicao = "eleicao";
	public static String cl_candidato = "candidato";
	
	public MongoClient getConexao() {
		MongoClient mongo = new MongoClient( "localhost" , 27017 );
		return mongo;
	}
	
	public MongoDatabase getDb() {
		MongoClient conn = this.getConexao();
		return conn.getDatabase(ConexaoMongo.db);
	}
	
	public MongoCollection<Document> getColecao(String colecao) {
		
		MongoCollection<Document> lista = this.getDb().getCollection(colecao);
		if (lista == null){
			try {
				this.getDb().createCollection(colecao);
			}catch(Exception ex) {
				ex.printStackTrace();
				return null;
			}
			return this.getDb().getCollection(colecao);
		}else {
			return lista;
		}
	}	
}
