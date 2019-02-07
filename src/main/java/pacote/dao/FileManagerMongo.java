package pacote.dao;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.client.gridfs.model.GridFSFile;

import pacote.bean.CandidatoBean;
import pacote.utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient; 
import com.mongodb.client.MongoCollection;

public class FileManagerMongo {
	
	public static String cl_files = "files";
	
	public GridFSBucket manager;

	
	public FileManagerMongo(MongoDatabase db) {
		try {
			this.manager = GridFSBuckets.create(db, FileManagerMongo.cl_files);
			MongoClient mongoClient = new MongoClient();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public GridFSFile getFotoCandidato(CandidatoBean candidato) {
		GridFSFile arquivo = null;
		try {
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("metadata.id_candidato", new ObjectId(candidato.getId() ) );
			GridFSFindIterable lista = this.manager.find(whereQuery);
			arquivo = lista.first();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return arquivo;
	}
	
	public String getTipoFoto(CandidatoBean candidato) {
		String retorno = null;
		try {
			GridFSFile arquivo = this.getFotoCandidato(candidato);
			Document metadados = arquivo.getMetadata();
			retorno = metadados.getString("content-type");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
	
	public InputStream getFotoCandidatoDownload(CandidatoBean candidato) {
		GridFSFile arquivo = null;
		//GridFSDownloadStream stream = null;
		InputStream in = null;
		try {
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("metadata.id_candidato", new ObjectId(candidato.getId() ) );
			GridFSFindIterable lista = this.manager.find(whereQuery);
			arquivo = lista.first();
			
			//stream = this.manager.openDownloadStream(arquivo.getId());
			if(arquivo != null) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    this.manager.downloadToStream(arquivo.getId(), baos);
			    
			    byte[] imageInByte = baos.toByteArray();
			    in = new ByteArrayInputStream(imageInByte);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return in;
	}
	
	
	public byte[] getFotoCandidatoBinary(CandidatoBean candidato) {
		GridFSFile arquivo = null;
		byte[] imageInByte = null;
		try {
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("metadata.id_candidato", new ObjectId(candidato.getId() ) );
			GridFSFindIterable lista = this.manager.find(whereQuery);
			arquivo = lista.first();			
			if(arquivo != null) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    this.manager.downloadToStream(arquivo.getId(), baos);			    
			    imageInByte = baos.toByteArray();			    
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return imageInByte;
	}
	
	
	public boolean salvarFotoCandidato(InputStream streamToUploadFrom, CandidatoBean candidato, String extensao, String mime) {
		boolean retorno = false;
		try {
			Document doc = new Document();
			doc.put("id_candidato", new ObjectId(candidato.getId()));
			doc.put("data_atualizacao", new Date());
			doc.put("content-type", mime);
			
			GridFSUploadOptions options = new GridFSUploadOptions()
                    .chunkSizeBytes(1024)
                    .metadata(doc);
			GridFSFile arquivo = this.getFotoCandidato(candidato);
			if(arquivo != null) {
				this.manager.delete(arquivo.getId());
			}
			
				
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
