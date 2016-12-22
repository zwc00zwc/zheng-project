import common.reg.zookeeper.AbstractListener;
import common.reg.zookeeper.ZookeeperConfig;
import common.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;

/**
 * Created by XR on 2016/12/22.
 */
public class Test3 {
    public static void main(String[] args){
        ZookeeperConfig zookeeperConfig=new ZookeeperConfig();
        zookeeperConfig.setServerLists("127.0.0.1:2181");
        zookeeperConfig.setNamespace("root");
        zookeeperConfig.setAuth("auth");
        ZookeeperRegistryCenter zookeeperRegistryCenter= null;
        try {
            zookeeperRegistryCenter = new ZookeeperRegistryCenter(zookeeperConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        zookeeperRegistryCenter.init();
        CuratorFramework curatorFramework=(CuratorFramework) zookeeperRegistryCenter.getRawClient();
        TreeCache treeCache=new TreeCache(curatorFramework,"/aaa");

        treeCache.getListenable().addListener(new AbstractListener() {
        });
        try {
            treeCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (true){
            
        }
    }
}
