package businessmq.producter;

import businessmq.base.ExchangeType;
import businessmq.base.Message;
import businessmq.base.MqRegistryManeger;
import businessmq.base.ProducterContext;
import businessmq.config.MessageType;
import businessmq.config.MqConfig;
import businessmq.config.ProducterConfig;
import businessmq.db.DbConfig;
import businessmq.db.dal.BusinessMqDal;
import businessmq.log.MqLogManager;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Date;
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
        Message message=new Message();
        message.setMsg(msg);
        message.setMsgType(MessageType.MYSQL.getType());
        try {
            Map<Integer,DbConfig> dbConfigMap= producterConfig.getBlanceNode();
            DbConfig dbConfig= dbConfigMap.get(producterConfig.getNode());
            if (producterConfig.getNode()+1>dbConfigMap.size()){
                producterConfig.setNode(1);
            }else {
                producterConfig.setNode(producterConfig.getNode()+1);
            }
            BusinessMqDal dal=new BusinessMqDal();
            Integer id= dal.insertMq(dbConfig,msg);
            if (id!=null){
                message.setId(Integer.toString(id));
            }
        } catch (Exception e) {
            MqLogManager.log(producterConfig.getExchangeName()+"发送消息异常",
                    e.toString(),new Date());
        }
        String sendmsg= JSON.toJSONString(message);
        try {
            Channel channel= MqRegistryManeger.getMqChannel(producterConfig);
            Map<String,Set<String>> map= producterConfig.getQueueRoutingKey();
            if (!StringUtils.isEmpty(producterConfig.getExchangeName())&&producterConfig.getExchangeType()!=null){
                channel.exchangeDeclare(producterConfig.getExchangeName(),producterConfig.getExchangeType().getType());
                if (!map.isEmpty()){
                    for (Map.Entry<String, Set<String>> entry: map.entrySet()){
                        channel.queueDeclare(entry.getKey(), true, false, false, null);
                        Set<String> set= entry.getValue();
                        if (set!=null&&set.size()>0){
                            for (String routing:set){
                                channel.queueBind(entry.getKey(),producterConfig.getExchangeName(),routing);
                            }
                        }
                    }
                }
                if (ExchangeType.FANOUT.equals(producterConfig.getExchangeType())){
                    channel.basicPublish(producterConfig.getExchangeName(),"", MessageProperties.PERSISTENT_TEXT_PLAIN,sendmsg.getBytes());
                }else {
                    for (Map.Entry<String, Set<String>> entry: map.entrySet()){
                        Set<String> set= entry.getValue();
                        if (set!=null&&set.size()>0){
                            for (String routing:set){
                                channel.basicPublish(producterConfig.getExchangeName(),routing, MessageProperties.PERSISTENT_TEXT_PLAIN,sendmsg.getBytes());
                            }
                        }
                    }
                }
            }else {
                if (!map.isEmpty()){
                    for (Map.Entry<String, Set<String>> entry: map.entrySet()){
                        channel.queueDeclare(entry.getKey(), true, false, false, null);
                        channel.basicPublish("",entry.getKey(), MessageProperties.PERSISTENT_TEXT_PLAIN,sendmsg.getBytes());
                    }
                }
            }

        } catch (IOException e) {
            MqLogManager.log(producterConfig.getExchangeName()+"发送消息异常",
                    e.toString(),new Date());
        } catch (TimeoutException e) {
            MqLogManager.log(producterConfig.getExchangeName()+"发送消息异常",
                    e.toString(),new Date());
        }
    }
}
