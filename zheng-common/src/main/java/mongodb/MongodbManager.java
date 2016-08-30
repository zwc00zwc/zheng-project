package mongodb;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import utility.BeanUtility;

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

    /**
     * 分页查询
     * @param collection 表名
     * @param query 查询条件
     * @param pageNo 页数
     * @param pageSize 页大小
     * @return
     */
    public static List<DBObject> queryPage(DBCollection collection,BasicDBObject query,Integer pageNo,Integer pageSize){
        List<DBObject> list = new ArrayList<DBObject>();
        DBCursor cursor = collection.find(query).skip((pageNo-1)*pageSize).limit(pageSize);
        while (cursor.hasNext()){
            list.add(cursor.next());
        }
        return list;
    }

    /**
     * 查询
     * @param collection 表名
     * @param query 查询条件
     * @return
     */
    public static List<DBObject> query(DBCollection collection,BasicDBObject query){
        List<DBObject> list = new ArrayList<DBObject>();
        DBCursor cursor = collection.find(query);
        while (cursor.hasNext()){
            list.add(cursor.next());
        }
        return list;
    }
}
