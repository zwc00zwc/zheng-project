package spiservice.system;

import domain.dao.RoleDao;
import domain.model.system.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spi.system.RoleSPI;

import java.util.List;

/**
 * Created by XR on 2016/8/25.
 */
@Transactional
@Service("roleSPIService")
public class RoleSPIService implements RoleSPI {
    @Autowired
    private RoleDao roleDao;
    public List<Role> queryByMemberId(Long memberid) {
        return roleDao.queryByMemberId(memberid);
    }

    public List<Role> queryList() {
        return roleDao.queryList();
    }

    public Role insertRoleAndReturnId(Role role) {
        roleDao.insertRoleAndReturnId(role);
        return role;
    }
}
