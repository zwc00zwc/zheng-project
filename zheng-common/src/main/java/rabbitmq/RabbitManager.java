package rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by XR on 2016/12/6.
 */
public class RabbitManager {
    private static ThreadLocal<Connection> connectionThreadLocal=new ThreadLocal<Connection>();
    private static ThreadLocal<Channel> channel=new ThreadLocal<Channel>();

    public static Channel getChannel(){
        try {
            if (channel.get()==null){
                if (connectionThreadLocal.get()==null){
                    ConnectionFactory factory = new ConnectionFactory();
                    factory.setHost("127.0.0.1");
                    Connection connection = null;
                    connection = factory.newConnection();
                    connectionThreadLocal.set(connection);
                }
                channel.set(connectionThreadLocal.get().createChannel());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return channel.get();
    }
}
