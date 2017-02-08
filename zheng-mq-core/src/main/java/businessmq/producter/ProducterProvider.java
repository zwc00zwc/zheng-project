package businessmq.producter;

import businessmq.config.MqConfig;

/**
 * 生成者提供类
 * Created by alan.zheng on 2017/2/8.
 */
public class ProducterProvider {
    public void sendMessage(MqConfig mqConfig){
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("127.0.0.1");
//        Connection connection = factory.newConnection();
//        for (int i=0;i<10;i++){
//            Channel channel = connection.createChannel();
//            channel.exchangeDeclare("","");
//            channel.queueDeclare("command", true, false, false, null);
//            channel.queueBind("","","");
//
//            String message = "Hello World!"+i+"";
//            channel.basicPublish("", "command", null, message.getBytes());
//            System.out.println(" ["+i+"] Sent '" + message + "'");
//        }
    }
}
