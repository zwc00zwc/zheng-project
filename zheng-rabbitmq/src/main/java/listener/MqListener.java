package listener;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.rabbitmq.client.Channel;
import common.mongodb.MongodbManager;
import org.bson.Document;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import common.rabbitmq.RabbitManager;

import java.io.IOException;
import java.util.Date;

/**
 * Created by XR on 2016/12/6.
 */
public class MqListener implements MessageListener {
    public void onMessage(Message message) {
        try {
            Channel channel= RabbitManager.getChannel();
            channel.queueDeclare("queue_two", true, false, false, null);
            String sendmessage = "Hello World!";
            channel.basicPublish("", "queue_two", null, sendmessage.getBytes());
            MongoDatabase database= MongodbManager.getDatabase("dbname");
            MongoCollection collection= database.getCollection("wait");
            Document document=new Document();
            document.append("detail","message提交发送");
            document.append("createtime",new Date());
            collection.insertOne(document);
        } catch (IOException e) {
            MongoDatabase database=MongodbManager.getAuthDatabase();
            MongoCollection collection= database.getCollection("waiterror");
            Document document=new Document();
            document.append("detail","提交发送消费失败");
            document.append("createtime",new Date());
            collection.insertOne(document);
        }
    }
}
