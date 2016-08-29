package spiservice.system;

import domain.dao.PermDao;
import domain.dao.RoleDao;
import domain.dto.RolePermDto;
import domain.model.system.Perm;
import domain.model.system.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spi.system.RoleSPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XR on 2016/8/25.
 */
@Transactional
@Service("roleSPIService")
public class RoleSPIService implements RoleSPI {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermDao permDao;
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

    public RolePermDto queryDtoById(Long id) {
        RolePermDto rolePermDto=new RolePermDto();
        Role role= roleDao.queryById(id);
        rolePermDto.setId(role.getId());
        rolePermDto.setRoleName(role.getRoleName());
        rolePermDto.setDisplayName(role.getDisplayName());
        rolePermDto.setRemark(role.getRemark());
        List<Perm> perms1= permDao.queryByRoleIdAndType(id,0);
        List<Long> ids1=new ArrayList<Long>();
        for (Perm perm:perms1) {
            ids1.add(perm.getId());
        }
        rolePermDto.setPermids1(ids1);
        List<Perm> perms2= permDao.queryByRoleIdAndType(id,1);
        List<Long> ids2=new ArrayList<Long>();
        for (Perm perm:perms2) {
            ids2.add(perm.getId());
        }
        rolePermDto.setPermids2(ids2);
        List<Perm> perms3= permDao.queryByRoleIdAndType(id,1);
        List<Long> ids3=new ArrayList<Long>();
        for (Perm perm:perms3) {
            ids3.add(perm.getId());
        }
        rolePermDto.setPermids3(ids3);
        return rolePermDto;
    }

    public int deleteRole(Long id) {
        return roleDao.deleteRole(id);
    }
}
