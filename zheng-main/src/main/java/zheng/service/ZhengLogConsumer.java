package zheng.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zheng.log.core.kafka.AbstractConsumer;

/**
 * Created by alan.zheng on 2017/11/8.
 */
public class ZhengLogConsumer implements AbstractConsumer {
    private static Logger logger = LoggerFactory.getLogger(ZhengLogConsumer.class);
    public void work(String s) {

    }

    public void work(ConsumerRecord<String, String> record) {
//        System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        logger.info("消费消息" + record.value());
    }
}
