package businessmq;

import businessmq.config.ProducterConfig;
import businessmq.producter.ProducterProvider;

/**
 * Created by alan.zheng on 2017/2/14.
 */
public class SpringProductProvide {
    private final ProducterConfig producterConfig;

    public SpringProductProvide(ProducterConfig _producterConfig){
        producterConfig=_producterConfig;
    }
    /**
     * 初始化消息监听
     */
    public void send(String msg){
        ProducterProvider consumerProvider=new ProducterProvider();
        consumerProvider.sendMessage(producterConfig,msg);
    }
}
