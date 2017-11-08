package zheng.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import zheng.log.core.kafka.ConsumerListener;
import zheng.log.core.kafka.KafkaConsumerManager;
import zheng.log.core.kafka.SpringConsumerListener;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * Created by alan.zheng on 2017/11/8.
 */
@Configuration
public class ZhengLogConsumerListenerConfig {
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Bean(initMethod = "init",name = "kafkaConsumerManager" )
    public KafkaConsumerManager kafkaConsumerManager() {
        return new KafkaConsumerManager("192.168.48.129:9092,192.168.48.131:9092,192.168.48.132:9092","testpart1-group", Arrays.asList("testpart1"));
    }

    @Bean(name = "zhengLogConsumer")
    public ZhengLogConsumer zhengLogConsumer() {
        return new ZhengLogConsumer();
    }

    @Bean(initMethod = "init",name = "consumerListener")
    public ConsumerListener consumerListener(final ZhengLogConsumer zhengLogConsumer, KafkaConsumerManager kafkaConsumerManager) {
        return new SpringConsumerListener(zhengLogConsumer.getClass().getCanonicalName(),zhengLogConsumer,threadPoolTaskExecutor,kafkaConsumerManager);
    }
}
