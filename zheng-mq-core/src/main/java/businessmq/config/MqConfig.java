package businessmq.config;

import businessmq.base.ExchangeType;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by alan.zheng on 2017/2/8.
 */
public class MqConfig {
    /**
     * ip
     */
    private String host;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
