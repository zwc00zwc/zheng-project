package businessmq.config;

import businessmq.base.ExchangeType;

import java.util.List;
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
     * 队列
     */
    private String queue;
    /**
     * 消息队列的
     */
    private Set<ExchangeType> exchangeTypes;

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

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public Set<ExchangeType> getExchangeTypes() {
        return exchangeTypes;
    }

    public void setExchangeTypes(Set<ExchangeType> exchangeTypes) {
        this.exchangeTypes = exchangeTypes;
    }
}
