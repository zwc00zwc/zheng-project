import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rabbitmq.RabbitManager;

import java.io.IOException;

/**
 * Created by XR on 2016/12/6.
 */
@SpringBootApplication
public class MqListener {
    public static void listen(){
        //区分不同工作进程的输出
        //int hashCode = Work.class.hashCode();
        //创建连接和频道
        try {
            Channel channel = RabbitManager.getChannel();
            //声明队列
            channel.queueDeclare("command", true, false, false, null);
            //System.out.println(hashCode
            //       + " [*] Waiting for messages. To exit press CTRL+C");

            QueueingConsumer consumer = new QueueingConsumer(channel);
            // 指定消费队列
            channel.basicConsume("command", true, consumer);
            while (true)
            {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());

                System.out.println("message:"+message);
                //System.out.println(hashCode + " [x] Received '" + message + "'");
                //doWork(message);
                //System.out.println(hashCode + " [x] Done");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
