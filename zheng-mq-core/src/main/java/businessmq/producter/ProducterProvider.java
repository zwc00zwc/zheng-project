package businessmq.producter;

import businessmq.base.ExchangeType;
import businessmq.base.MqRegistryManeger;
import businessmq.config.MqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

/**
 * 生成者提供类
 * Created by alan.zheng on 2017/2/8.
 */
public class ProducterProvider {
    /**
     * 发送消息
     * @param mqConfig
     * @param msg
     */
    public void sendMessage(MqConfig mqConfig,String msg){
        try {
            Channel channel= MqRegistryManeger.getMqChannel(mqConfig);
            Map<String,Set<String>> map= mqConfig.getQueueRoutingKey();
            if (!StringUtils.isEmpty(mqConfig.getExchangeName())&&mqConfig.getExchangeType()!=null){
                channel.exchangeDeclare(mqConfig.getExchangeName(),mqConfig.getExchangeType().getType());
                if (!map.isEmpty()){
                    for (Map.Entry<String, Set<String>> entry: map.entrySet()){
                        channel.queueDeclare(entry.getKey(), true, false, false, null);
                        Set<String> set= entry.getValue();
                        for (String routing:set){
                            channel.queueBind(entry.getKey(),mqConfig.getExchangeName(),routing);
                        }
                    }
                }
                if (ExchangeType.FANOUT.equals(mqConfig.getExchangeType())){
                    channel.basicPublish(mqConfig.getExchangeName(),"", MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes());
                }else {
                    for (Map.Entry<String, Set<String>> entry: map.entrySet()){
                        Set<String> set= entry.getValue();
                        for (String routing:set){
                            channel.basicPublish(mqConfig.getExchangeName(),routing, MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes());
                        }
                    }
                }
            }else {
                if (!map.isEmpty()){
                    for (Map.Entry<String, Set<String>> entry: map.entrySet()){
                        channel.queueDeclare(entry.getKey(), true, false, false, null);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
