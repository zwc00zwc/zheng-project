package mongodb;

import com.mongodb.MongoClient;

/**
 * Created by Administrator on 2016/8/21.
 */
public class MongodbManager {
    public static MongoClient getMongoClient(String host, int port){
        MongoClient client=new MongoClient(host,port);
        return client;
    }
}
