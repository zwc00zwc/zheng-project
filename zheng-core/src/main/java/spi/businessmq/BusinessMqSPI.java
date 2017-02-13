package spi.businessmq;

import domain.model.PageModel;
import domain.model.mq.BusinessMq;
import domain.model.mq.query.BusinessMqQuery;

/**
 * Created by alan.zheng on 2017/2/13.
 */
public interface BusinessMqSPI {
    PageModel<BusinessMq> queryBusinessMqPage(BusinessMqQuery query);
}
