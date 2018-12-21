package pacote.dao;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

import pacote.bean.CandidatoBean;
import pacote.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient; 
import com.mongodb.client.MongoCollection;

public class FileManagerMongo {
	
	public static String cl_files = "files";
	
	public GridFSBucket manager;

	
	public FileManagerMongo(MongoDatabase db) {
		try {
			this.manager = GridFSBuckets.create(db, FileManagerMongo.cl_files);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public boolean salvarFotoCandidato(File file, CandidatoBean candidato, String extensao) {
		boolean retorno = false;
		try {
			Document doc = new Document();
			doc.put("id_candidato", new ObjectId(candidato.getId()));
			doc.put("data_atualizacao", new Date());
			
			GridFSUploadOptions options = new GridFSUploadOptions()
                    .chunkSizeBytes(1024)
                    .metadata(doc);
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("_id", new ObjectId(candidato.getId() ) );
			GridFSFindIterable lista = this.manager.find(whereQuery);
			com.mongodb.client.gridfs.model.GridFSFile arquivo = lista.first();
			if(arquivo != null) {
				this.manager.delete(arquivo.getId());
			}
			
			InputStream streamToUploadFrom = new FileInputStream(file);
				
			ObjectId fileId = this.manager.uploadFromStream(candidato.getId() + "." + extensao, streamToUploadFrom, options);
			
			if(fileId != null) {
				retorno = true;
			}else {
				throw new Exception("Erro no upload do arquivo");
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
}
