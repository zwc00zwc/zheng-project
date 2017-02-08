# zheng-project
分布式基础框架

项目结构

zheng-common    基础类库

zheng-core      数据访问依赖

zheng-job-core  任务管理依赖

zheng-service   dubbo服务(提供数据服务)

zheng-web       后台网站

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

