package pacote.dao;

import com.mongodb.client.MongoDatabase;

import pacote.bean.CandidatoBean;
import pacote.bean.CargoBean;
import pacote.bean.EleicaoBean;
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
	
	public boolean checarDuplicidadeCPF(CandidatoBean campos) {
		try {
			ConexaoMongo conn = new ConexaoMongo();
			MongoDatabase db = conn.getDb();
			MongoCollection<Document> colection = conn.getColecao(ConexaoMongo.cl_candidato);
			if(colection != null) {
				MongoCursor<Document> cursor = colection.find(eq("cpf", campos.getCpf())).iterator();
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
}
