package domain.dao;

import domain.model.system.RolePerm;

import java.util.List;
import java.util.Map;

/**
 * Created by XR on 2016/8/22.
 */
public interface RolePermDao {

    List<String> queryPermIdByRoleIds(List<Long> roleids);

    String queryPermsByRoleId(Long roleid);

    RolePerm queryByRoleId(Long roleid);

    int insertRolePerm(RolePerm rolePerm);

    int updateById(RolePerm rolePerm);

    int deleteByRoleId(Long roleid);
}
