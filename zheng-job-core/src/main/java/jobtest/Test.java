package jobtest;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.state.ConnectionState;
import reg.base.AbstractConnectListener;
import reg.base.AbstractListener;
import reg.zookeeper.ZookeeperConfig;
import reg.zookeeper.ZookeeperRegistryCenter;

import java.util.Date;

/**
 * Created by XR on 2016/12/16.
 */
public class Test {
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
        curatorFramework.getConnectionStateListenable().addListener(new AbstractConnectListener(){
            @Override
            public void changed(CuratorFramework curatorFramework, ConnectionState connectionState) {
                System.out.print("连接状态已改变");
                if (ConnectionState.LOST.equals(connectionState)){
                    System.out.print("断开连接");
                }
            }
        });
        TreeCache treeCache=new TreeCache(curatorFramework,"/aaa");

        treeCache.getListenable().addListener(new AbstractListener() {
            @Override
            public void changed(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) {
                System.out.print("发生了监听:"+new Date().toString());
                String str=new String(treeCacheEvent.getData().getData());
                System.out.print("改变的数据"+str);
            }
        });
        try {
            treeCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        zookeeperRegistryCenter.create("/aaa","aaa");
        zookeeperRegistryCenter.update("/aaa","111");
        zookeeperRegistryCenter.update("/aaa","222");

//        ZkTest zkTest=new ZkTest();
//        zkTest.setdata();
//        zkTest.setdata();
//        zkTest.setdata();
//        zkTest.delete();
//        while (true){
//            DistributedLock lock = null;
//            try {
//                lock=new DistributedLock("127.0.0.1:2181","test");
//                lock.lock();
//                Thread.sleep(1000);
//                System.out.print("Test:"+new Date().toString());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            finally {
//                if(lock != null)
//                    lock.unlock();
//            }
//        }
    }
}
