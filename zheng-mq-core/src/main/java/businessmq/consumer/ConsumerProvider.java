package businessmq.consumer;

import businessmq.base.MqRegistryManeger;
import businessmq.config.MqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

/**
 * 消费者提供类
 * Created by alan.zheng on 2017/2/8.
 */
public class ConsumerProvider {

    public void receiveMessage(MqConfig mqConfig){
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
            }else {
                if (!map.isEmpty()){
                    for (Map.Entry<String, Set<String>> entry: map.entrySet()){
                        channel.queueDeclare(entry.getKey(), true, false, false, null);
                    }
                }
            }
            QueueingConsumer consumer = new QueueingConsumer(channel);
            // 指定消费队列
            if (StringUtils.isEmpty(mqConfig.getConsumerQueue())){
                channel.basicConsume(channel.queueDeclare().getQueue(), true, consumer);
            }else {
                channel.basicConsume(mqConfig.getConsumerQueue(), true, consumer);
            }

            while (true)
            {
                try {
                    QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                    String message = new String(delivery.getBody());

                    //业务代码
                    System.out.println("message:"+message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
