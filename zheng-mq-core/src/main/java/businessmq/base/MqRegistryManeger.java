package businessmq.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import businessmq.config.MqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by alan.zheng on 2017/2/8.
 */
public class MqRegistryManeger {
    private static MqRegistryManeger mqRegistryManeger;

    private static Map<String,Connection> connectionMap=new HashMap<>();

    private static Map<String,Channel> channelMap=new HashMap<>();

    public static MqRegistryManeger instance(){
        if (mqRegistryManeger==null){
            mqRegistryManeger=new MqRegistryManeger();
        }
        return mqRegistryManeger;
    }

    public static Channel getMqChannel(MqConfig config){
        String key=config.getHost()+":"+config.getPort()+"/"+config.getUserName();
        Channel channel= channelMap.get(key);
        if (channel==null){
            Connection connection= connectionMap.get(key);
            if (connection==null){
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost(config.getHost());
                factory.setPort(config.getPort());
                factory.setUsername(config.getUserName());
                factory.setPassword(config.getPassword());
                try {
                    connection = factory.newConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                connectionMap.put(key,connection);
            }
            try {
                channel=connection.createChannel();
            } catch (IOException e) {
                e.printStackTrace();
            }
            channelMap.put(key,channel);
        }
        return channel;
    }
}
