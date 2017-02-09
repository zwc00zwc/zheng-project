# zheng-project
分布式基础框架

项目结构

zheng-common    基础类库

zheng-core      数据访问依赖

zheng-job-core  任务管理依赖

zheng-service   dubbo服务(提供数据服务)

zheng-web       后台网站

任务管理平台用于监控任务的运行状态，调度任务执行，支持任务的暂停，手动执行，停止，动态修改执行时间等功能。
适配spring，具有良好的功能扩展性，稳定性，简单性，让第三方开发人员专注于任务业务开发，方便功能扩展。

任务管理开发需要加入任务管理依赖
<dependency>
    <groupId>zheng</groupId>
    <artifactId>zheng-job-core</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>


任务业务代码开发需实现BaseJob接口

public class ZhengJob implements BaseJob{

	public void execute(){

		System.out.print("我在测试ZhengJob");

	}
	
}

任务平台业务代码建议以spring boot进行开发
spring配置任务

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