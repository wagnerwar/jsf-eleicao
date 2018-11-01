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

public class CandidatoDB extends ConexaoMongo {
	
}
