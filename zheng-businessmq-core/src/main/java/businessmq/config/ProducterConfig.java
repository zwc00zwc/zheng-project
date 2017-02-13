package businessmq.config;

import businessmq.base.ExchangeType;

import java.util.Map;
import java.util.Set;

/**
 * Created by alan.zheng on 2017/2/10.
 */
public class ProducterConfig extends MqConfig {
    /**
     * 队列路由
     */
    private Map<String,Set<String>> queueRoutingKey;
    /**
     * 交换机名
     */
    private String exchangeName;
    /**
     * 交换机类型
     */
    private ExchangeType exchangeType;

    public Map<String, Set<String>> getQueueRoutingKey() {
        return queueRoutingKey;
    }

    public void setQueueRoutingKey(Map<String, Set<String>> queueRoutingKey) {
        this.queueRoutingKey = queueRoutingKey;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public ExchangeType getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(ExchangeType exchangeType) {
        this.exchangeType = exchangeType;
    }
}
