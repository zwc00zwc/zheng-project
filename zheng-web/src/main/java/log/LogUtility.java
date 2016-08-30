package log;

import com.mongodb.DBCollection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import mongodb.MongodbManager;
import org.bson.Document;

import java.util.Date;

/**
 * Created by XR on 2016/8/30.
 */
public class LogUtility {
    private static MongoDatabase mongoDatabase;
    public static void insertLog(String errortype,String error){
        mongoDatabase= MongodbManager.getMongoClient("127.0.0.1",27017,"zheng");
        MongoCollection collection= mongoDatabase.getCollection("log");
        Document document=new Document();
        document.append("error",errortype);
        document.append("detail",error);
        document.append("createtime",new Date());
        collection.insertOne(document);
    }
}
