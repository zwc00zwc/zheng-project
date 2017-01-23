package reg.listener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import reg.base.AbstractConnectListener;

/**
 * Created by alan.zheng on 2017/1/23.
 */
public class ConnectListener extends AbstractConnectListener {
    @Override
    public void changed(CuratorFramework curatorFramework, ConnectionState connectionState) {
        System.out.print("连接状态发生了变化");
    }
}
