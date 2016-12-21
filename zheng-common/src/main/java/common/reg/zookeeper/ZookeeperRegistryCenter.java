package common.reg.zookeeper;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import common.reg.base.RegistryCenter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * zk 连接注册实现类
 * Created by XR on 2016/12/21.
 */
public class ZookeeperRegistryCenter implements RegistryCenter {
    /**
     * zk config
     */
    private ZookeeperConfig zkConfig;
    /**
     * zk caches
     */
    private final Map<String, TreeCache> caches = new HashMap<String, TreeCache>();

    public ZookeeperRegistryCenter(ZookeeperConfig zookeeperConfig){
        zkConfig=zookeeperConfig;
    }
    /**
     * zk client
     */
    private CuratorFramework client;

    public void init(){
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString(zkConfig.getServerLists())
                .retryPolicy(new ExponentialBackoffRetry(zkConfig.getBaseSleepTimeMilliseconds(), zkConfig.getMaxRetries(), zkConfig.getMaxSleepTimeMilliseconds()))
                .namespace(zkConfig.getNamespace());
        if (0 != zkConfig.getSessionTimeoutMilliseconds()) {
            builder.sessionTimeoutMs(zkConfig.getSessionTimeoutMilliseconds());
        }
        if (0 != zkConfig.getConnectionTimeoutMilliseconds()) {
            builder.connectionTimeoutMs(zkConfig.getConnectionTimeoutMilliseconds());
        }
        if (!Strings.isNullOrEmpty(zkConfig.getAuth())) {
            builder.authorization("auth", zkConfig.getAuth().getBytes(Charsets.UTF_8))
                    .aclProvider(new ACLProvider() {

                        public List<ACL> getDefaultAcl() {
                            return ZooDefs.Ids.CREATOR_ALL_ACL;
                        }

                        public List<ACL> getAclForPath(final String path) {
                            return ZooDefs.Ids.CREATOR_ALL_ACL;
                        }
                    });
        }
        client = builder.build();
        client.start();
        try {
            if (!client.blockUntilConnected(zkConfig.getMaxSleepTimeMilliseconds() * zkConfig.getMaxRetries(), TimeUnit.MILLISECONDS)) {
                client.close();
                throw new KeeperException.OperationTimeoutException();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException.OperationTimeoutException e) {
            e.printStackTrace();
        }
    }

    public String getDirectly(String key) {
        return null;
    }

    public List<String> getChildrenKeys(String key) {
        return null;
    }

    public int getNumChildren(String key) {
        return 0;
    }

    public void persistEphemeral(String key, String value) {

    }

    public String persistSequential(String key, String value) {
        return null;
    }

    public void persistEphemeralSequential(String key) {

    }

    public void addCacheData(String cachePath) {

    }

    public Object getRawCache(String cachePath) {
        return null;
    }
}
