package spiservice.system;

import domain.dao.PermDao;
import domain.dao.RoleDao;
import domain.dao.RolePermDao;
import domain.dto.RolePermDto;
import domain.manager.PermManager;
import domain.manager.RoleManager;
import domain.model.system.Perm;
import domain.model.system.Role;
import domain.model.system.RolePerm;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spi.system.RoleSPI;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private RolePermDao rolePermDao;
    @Autowired
    private PermManager permManager;
    @Autowired
    private RoleManager roleManager;
    public List<Role> queryByMemberId(Long memberid) {
        return roleManager.queryByMemberId(memberid);
    }

    public List<Role> queryList() {
        return roleDao.queryList();
    }

    public boolean insertRole(Role role,String ids) {
        return roleManager.insertRole(role,ids);
    }

    public RolePermDto queryDtoById(Long id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        RolePermDto rolePermDto=new RolePermDto();
        Role role= roleManager.queryById(id);
        PropertyUtils.copyProperties(rolePermDto,role);
        List<Perm> perms1= permManager.queryByRoleIdAndType(id,1);
        List<Long> ids1=new ArrayList<Long>();
        for (Perm perm:perms1) {
            ids1.add(perm.getId());
        }
        rolePermDto.setPermids1(ids1);
        List<Perm> perms2= permManager.queryByRoleIdAndType(id,2);
        List<Long> ids2=new ArrayList<Long>();
        for (Perm perm:perms2) {
            ids2.add(perm.getId());
        }
        rolePermDto.setPermids2(ids2);
        List<Perm> perms3= permManager.queryByRoleIdAndType(id,3);
        List<Long> ids3=new ArrayList<Long>();
        for (Perm perm:perms3) {
            ids3.add(perm.getId());
        }
        rolePermDto.setPermids3(ids3);
        return rolePermDto;
    }

    public int deleteRole(Long id) {
        return permManager.deleteById(id);
    }
}
