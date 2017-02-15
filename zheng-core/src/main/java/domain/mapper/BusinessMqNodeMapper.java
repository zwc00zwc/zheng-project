package domain.mapper;

import domain.model.mq.BusinessMqNode;
import domain.model.mq.query.BusinessMqNodeQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by alan.zheng on 2017/2/15.
 */
public interface BusinessMqNodeMapper {
    BusinessMqNode queryById(Long nodeId);

    List<BusinessMqNode> queryList();

    List<BusinessMqNode> queryPageList(@Param("query") BusinessMqNodeQuery query);

    int queryCountPage(@Param("query") BusinessMqNodeQuery query);

    int insertNode(@Param("node") BusinessMqNode job);

    int deleteNode(Long nodeId);
}
