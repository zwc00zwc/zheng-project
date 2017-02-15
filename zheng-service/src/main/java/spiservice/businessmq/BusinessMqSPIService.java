package spiservice.businessmq;

import common.utility.PropertiesUtility;
import domain.manager.BusinessMqManager;
import domain.model.PageModel;
import domain.model.mq.BusinessMq;
import domain.model.mq.BusinessMqLog;
import domain.model.mq.BusinessMqNode;
import domain.model.mq.query.BusinessMqLogQuery;
import domain.model.mq.query.BusinessMqNodeQuery;
import domain.model.mq.query.BusinessMqQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reg.zookeeper.ZookeeperConfig;
import reg.zookeeper.ZookeeperRegistryCenter;
import spi.businessmq.BusinessMqSPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alan.zheng on 2017/2/13.
 */
@Service("businessMqSPIService")
public class BusinessMqSPIService implements BusinessMqSPI {
    @Autowired
    private BusinessMqManager businessMqManager;

    public PageModel<BusinessMq> queryBusinessMqPage(BusinessMqQuery query) {
        ZookeeperConfig zookeeperConfig=new ZookeeperConfig();
        PropertiesUtility propertiesUtility=new PropertiesUtility("zookeeper.properties");
        zookeeperConfig.setServerLists(propertiesUtility.getProperty("zkmq.serverList"));
        zookeeperConfig.setNamespace(propertiesUtility.getProperty("zkmq.namespace"));
        zookeeperConfig.setAuth(propertiesUtility.getProperty("zkmq.auth"));
        ZookeeperRegistryCenter zookeeperRegistryCenter= null;
        try {
            zookeeperRegistryCenter = new ZookeeperRegistryCenter(zookeeperConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        zookeeperRegistryCenter.init();
        List<String> keys= zookeeperRegistryCenter.getChildrenKeys("/mq");
        List<BusinessMq> list=new ArrayList<BusinessMq>();
        for (int i=0;i<keys.size();i++){
            BusinessMq businessmq=new BusinessMq();
            businessmq.setMqName(keys.get(i));
            businessmq.setMqRemark(zookeeperRegistryCenter.get("/mq/"+keys.get(i)));
            businessmq.setStatus(1);
            list.add(businessmq);
        }
        zookeeperRegistryCenter.close();
        return new PageModel<BusinessMq>(list,query.getCurrPage(),keys.size(),query.getPageSize());
    }

    public PageModel<BusinessMqNode> queryBusinessMqNodePage(BusinessMqNodeQuery query) {
        return businessMqManager.queryPageList(query);
    }

    public PageModel<BusinessMqLog> queryBusinessMqLogPage(BusinessMqLogQuery query) {
        return businessMqManager.queryBusinessMqLogPage(query);
    }

    public boolean insertNode(BusinessMqNode businessMqNode) {
        return businessMqManager.insertNode(businessMqNode);
    }
}
