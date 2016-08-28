package domain.dao;

import domain.model.system.RolePerm;

import java.util.List;

/**
 * Created by XR on 2016/8/22.
 */
public interface RolePermDao {
    List<RolePerm> queryListByRoleId(Long roleid);

    int insertRolePermByList(List<RolePerm> rolePerm);

    int deleteByRoleId(Long roleid);
}
