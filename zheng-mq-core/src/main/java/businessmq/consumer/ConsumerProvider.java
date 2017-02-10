package businessmq.consumer;

import businessmq.base.MqRegistryManeger;
import businessmq.config.ConsumerConfig;
import businessmq.config.MqConfig;
import com.google.common.base.Optional;
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

    public void receiveMessage(ConsumerConfig consumerConfig,AbstractConsumer abstractConsumer){
        try {
            Channel channel= MqRegistryManeger.getMqChannel(consumerConfig);
            if (!StringUtils.isEmpty(consumerConfig.getExchangeName())&&consumerConfig.getExchangeType()!=null){
                channel.exchangeDeclare(consumerConfig.getExchangeName(),consumerConfig.getExchangeType().getType());
                if (!StringUtils.isEmpty(consumerConfig.getConsumerQueue())){
                    channel.queueDeclare(consumerConfig.getConsumerQueue(), true, false, false, null);
                }
                if (consumerConfig.getRoutingKey()!=null&&consumerConfig.getRoutingKey().length>0){
                    for (int i=0;i<consumerConfig.getRoutingKey().length;i++){
                        channel.queueBind(consumerConfig.getConsumerQueue(),consumerConfig.getExchangeName(),consumerConfig.getRoutingKey()[i]);
                    }
                }
            }else {
                if (!StringUtils.isEmpty(consumerConfig.getConsumerQueue())){
                    channel.queueDeclare(consumerConfig.getConsumerQueue(), true, false, false, null);
                }
            }
            QueueingConsumer consumer = new QueueingConsumer(channel);
            // 指定消费队列
            if (StringUtils.isEmpty(consumerConfig.getConsumerQueue())){
                channel.basicConsume(channel.queueDeclare().getQueue(), true, consumer);
            }else {
                channel.basicConsume(consumerConfig.getConsumerQueue(), true, consumer);
            }

            while (true)
            {
                try {
                    QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                    String message = new String(delivery.getBody());
                    abstractConsumer.work(message);
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
