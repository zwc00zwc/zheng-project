package businessmq.producter;

import businessmq.base.ExchangeType;
import businessmq.base.MqRegistryManeger;
import businessmq.config.MqConfig;
import businessmq.config.ProducterConfig;
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
     * @param producterConfig
     * @param msg
     */
    public void sendMessage(ProducterConfig producterConfig, String msg){
        try {
            Channel channel= MqRegistryManeger.getMqChannel(producterConfig);
            Map<String,Set<String>> map= producterConfig.getQueueRoutingKey();
            if (!StringUtils.isEmpty(producterConfig.getExchangeName())&&producterConfig.getExchangeType()!=null){
                channel.exchangeDeclare(producterConfig.getExchangeName(),producterConfig.getExchangeType().getType());
                if (!map.isEmpty()){
                    for (Map.Entry<String, Set<String>> entry: map.entrySet()){
                        channel.queueDeclare(entry.getKey(), true, false, false, null);
                        Set<String> set= entry.getValue();
                        for (String routing:set){
                            channel.queueBind(entry.getKey(),producterConfig.getExchangeName(),routing);
                        }
                    }
                }
                if (ExchangeType.FANOUT.equals(producterConfig.getExchangeType())){
                    channel.basicPublish(producterConfig.getExchangeName(),"", MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes());
                }else {
                    for (Map.Entry<String, Set<String>> entry: map.entrySet()){
                        Set<String> set= entry.getValue();
                        for (String routing:set){
                            channel.basicPublish(producterConfig.getExchangeName(),routing, MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes());
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
