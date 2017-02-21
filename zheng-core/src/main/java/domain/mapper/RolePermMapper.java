package domain.mapper;

import domain.model.system.RolePerm;

import java.util.List;

/**
 * Created by XR on 2016/8/22.
 */
public interface RolePermMapper {

    List<String> queryPermIdByRoleIds(List<Long> roleids);

    String queryPermsByRoleId(Long roleid);

    RolePerm queryByRoleId(Long roleid);

    int insertRolePerm(RolePerm rolePerm);

    int updateById(RolePerm rolePerm);

    int updateByRoleId(RolePerm rolePerm);

    int deleteByRoleId(Long roleid);
}
