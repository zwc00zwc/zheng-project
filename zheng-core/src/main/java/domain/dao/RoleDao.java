package domain.dao;

import domain.model.system.Role;

import java.util.List;

/**
 * Created by XR on 2016/8/22.
 */
public interface RoleDao {
    List<Role> queryByIds(List<Long> ids);

    Role queryById(Long id);

    List<Role> queryList();

    int insertRoleAndReturnId(Role role);

    int updateRole(Role role);

    int deleteRole(Long id);
}
