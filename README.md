# zheng-project
分布式基础框架


任务系统任务开发需要已spring boot进行开发
maven pom.xml加入任务所需jar
<dependency>
    <groupId>zheng</groupId>
    <artifactId>zheng-job-core</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>

任务业务代码开发继承SimpleJob
public class ZhengJob implements SimpleJob {
    
    public void execute() {
        System.out.print("我在测试ZhengJob"+DateUtility.getStrFromDate(new Date(),""));
    }
}

