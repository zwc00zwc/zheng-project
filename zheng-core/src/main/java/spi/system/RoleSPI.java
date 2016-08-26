package spi.system;

import domain.model.system.Role;

import java.util.List;

/**
 * Created by XR on 2016/8/25.
 */
public interface RoleSPI {
    List<Role> queryByMemberId(Long memberid);

    List<Role> queryList();

    int insertRoleAndReturnId(Role role);
}
