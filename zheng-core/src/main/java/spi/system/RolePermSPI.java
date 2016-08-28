package spi.system;

import domain.model.system.RolePerm;

import java.util.List;

/**
 * Created by XR on 2016/8/26.
 */
public interface RolePermSPI {
    int insertRolePermByList(String ids,Long roleid);

    int deleteByRoleId(Long roleid);
}
