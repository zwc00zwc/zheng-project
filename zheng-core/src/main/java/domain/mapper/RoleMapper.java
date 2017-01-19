package domain.mapper;

import domain.model.system.Role;
import domain.model.system.query.RoleQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by XR on 2016/8/22.
 */
public interface RoleMapper {
    List<Role> queryByIds(List<Long> ids);

    Role queryById(Long id);

    List<Role> queryList();

    List<Role> queryPageList(@Param("query") RoleQuery query);

    int queryCountPage(@Param("query")RoleQuery query);

    int insertRoleAndReturnId(Role role);

    int updateRole(Role role);

    Role queryByName(String name);

    int deleteRole(Long id);
}
