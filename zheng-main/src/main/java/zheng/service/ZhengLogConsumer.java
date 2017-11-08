package zheng.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import zheng.log.core.kafka.AbstractConsumer;

/**
 * Created by alan.zheng on 2017/11/8.
 */
public class ZhengLogConsumer implements AbstractConsumer {
    public void work(String s) {

    }

    public void work(ConsumerRecord<String, String> record) {
        System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
    }
}
