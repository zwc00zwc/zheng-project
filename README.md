# zheng-project
分布式基础框架

项目结构
zheng-businessmq-core  业务消息队列中间件依赖

zheng-common    基础类库

zheng-core      数据访问依赖

zheng-job-core  任务管理依赖

zheng-service   dubbo服务(提供数据服务)

zheng-web       后台网站

##任务平台##
任务管理平台用于监控任务的运行状态，调度任务执行，支持任务的暂停，手动执行，停止，动态修改执行时间等功能。
适配spring，具有良好的功能扩展性，稳定性，简单性，让第三方开发人员专注于任务业务开发，方便功能扩展。

任务管理开发需要加入任务管理依赖
```
<dependency>
    <groupId>zheng</groupId>
    <artifactId>zheng-job-core</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

任务业务代码开发需实现BaseJob接口

```
public class ZhengJob implements BaseJob{
	public void execute(){
		System.out.print("我在测试ZhengJob");
	}
}
```

任务平台业务代码建议以spring boot进行开发
spring配置任务
```
@Configuration

public class ZhengJobConfig {

    @Bean(name = "zhengJob")

    public ZhengJob zhengJob() {

        return new ZhengJob();

    }

    @Resource
    public ZookeeperRegistryCenter registryCenter;

    @Bean(initMethod = "init",name = "zhengJobSpringJobScheduler")
    public JobScheduler dataflowJobScheduler(final BaseJob zhengJob) {
        JobConfig jobConfig=new JobConfig("zhengJob", zhengJob.getClass().getCanonicalName());
        return new SpringJobScheduler(jobConfig,registryCenter,zhengJob);
    }
}
```
##任务平台效果截图##

<p>
    <img src="https://github.com/zwc00zwc/zheng-project/blob/master/doc/4.png" style="float:none;"/>
</p>
<p>
    <img src="https://github.com/zwc00zwc/zheng-project/blob/master/doc/1.png" style="float:none;"/>
</p>
<p>
    <img src="https://github.com/zwc00zwc/zheng-project/blob/master/doc/2.png" style="float:none;"/>
</p>
<p>
    <img src="https://github.com/zwc00zwc/zheng-project/blob/master/doc/3.png" style="float:none;"/>
</p>
<p>
    <img src="https://github.com/zwc00zwc/zheng-project/blob/master/doc/5.png" style="float:none;"/>
</p>


#业务消息队列#
业务消息队列中间件基于rabbitmq进行二次开发，支持消息监听实时监控，日志集中查询，适配spring，具有良好的功能扩展性，稳定性，简单性，让第三方开发人员专注于任务业务开发，方便功能扩展。
任务管理开发需要加入任务管理依赖
```
<dependency>
    <groupId>zheng</groupId>
    <artifactId>zheng-businessmq-core</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

spring配置消息队列发送事例(spring boot)
```
@Configuration
public class MqProducterConfig {
    @Bean
    public ProducterProvider producterProvider(){
        return new ProducterProvider();
    }

    @Bean
    public ProducterConfig config(){
        ProducterConfig producterConfig=new ProducterConfig();
        producterConfig.setNode(1);
        producterConfig.setHost("127.0.0.1");
        producterConfig.setPort(5672);
        producterConfig.setUserName("guest");
        producterConfig.setPassword("guest");
        Map queuemap=new HashMap();
        queuemap.put("command",null);
        producterConfig.setQueueRoutingKey(queuemap);
        return producterConfig;
    }
    @Bean
    public SpringProductProvide provide(ProducterConfig producterConfig,ProducterProvider producterProvider) {
        DbConfig dbConfig=new DbConfig();
        dbConfig.setDriver("com.mysql.jdbc.Driver");
        dbConfig.setUrl("jdbc:mysql://localhost:3306/com.zwc?useUnicode=true&amp;characterEncoding=UTF-8");
        dbConfig.setUsername("root");
        dbConfig.setPassword("root");
        return new SpringProductProvide(producterConfig,dbConfig,producterProvider);
    }
}

```


业务代码实现AbstractConsumer接口
```
public class TestConsumer implements AbstractConsumer{
    public void work(String s){
        System.out.print(s+DateUtility.getStrFromDate(new Date(),""))
    }
}
```

消息队列监听业务代码建议以spring boot进行开发
spring配置消息队列监听
```
@Configuration
public class TestMqConfig {
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor threadPoolExecutor=new ThreadPoolTaskExecutor();
        threadPoolExecutor.setCorePoolSize(5);
        threadPoolExecutor.setMaxPoolSize(10);
        threadPoolExecutor.setQueueCapacity(25);
        return new ThreadPoolTaskExecutor();
    }
    @Resource
    private ZookeeperRegistryCenter mqzookeeperRegistryCenter;
    @Bean(name = "testConsumer")
    public TestConsumer zhengJob() {
        return new TestConsumer();
    }
    @Bean(initMethod = "init",name = "TestListen")
    public ConsumerListener dataflowJobScheduler(final TestConsumer testConsumer,ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        ConsumerConfig consumerConfig=new ConsumerConfig();
        consumerConfig.setHost("127.0.0.1");
        consumerConfig.setPort(5672);
        consumerConfig.setUserName("guest");
        consumerConfig.setPassword("guest");
        consumerConfig.setConsumerQueue("command");
        consumerConfig.setJavaClass(testConsumer.getClass().getCanonicalName());
        return new SpringConsumerListener(consumerConfig,mqzookeeperRegistryCenter,testConsumer,threadPoolTaskExecutor);
    }    
}
```

##消息队列效果截图##

<p>
    <img src="https://github.com/zwc00zwc/zheng-project/blob/master/doc/6.png" style="float:none;"/>
</p>
<p>
    <img src="https://github.com/zwc00zwc/zheng-project/blob/master/doc/7.png" style="float:none;"/>
</p>
<p>
    <img src="https://github.com/zwc00zwc/zheng-project/blob/master/doc/8.png" style="float:none;"/>
</p>