package mongodb;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/21.
 */
public class MongodbManager {
    public static MongoDatabase getMongoClient(String host, int port,String database){
        MongoClient client=new MongoClient(host,port);

        return client.getDatabase(database);
    }

    public static MongoDatabase getMongoClient(String host, int port, String username, String database, String password){
        List<ServerAddress> addresses=new ArrayList<ServerAddress>();
        ServerAddress serverAddress=new ServerAddress(host,port);
        addresses.add(serverAddress);
        List<MongoCredential> credentials=new ArrayList<MongoCredential>();
        MongoCredential mongoCredential= MongoCredential.createScramSha1Credential(username,database,password.toCharArray());
        credentials.add(mongoCredential);
        MongoClient mongoClient=new MongoClient(addresses,credentials);
        return mongoClient.getDatabase(database);
    }

    /**
     * 插入
     * @param collection
     * @param basicDBObject  BasicDBObject实现DBObject接口
     */
    public static void insert(DBCollection collection,BasicDBObject basicDBObject){
        collection.insert(basicDBObject);
    }
}
