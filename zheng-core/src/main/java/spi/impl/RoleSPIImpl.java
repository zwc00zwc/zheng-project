package spi.impl;

import domain.dto.RolePermDto;
import domain.manager.PermManager;
import domain.manager.RoleManager;
import domain.model.PageModel;
import domain.model.system.Perm;
import domain.model.system.Role;
import domain.model.system.query.RoleQuery;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spi.system.RoleSPI;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alan.zheng on 2017/12/8.
 */
@Service("roleSPIService")
public class RoleSPIImpl implements RoleSPI {
    @Autowired
    private PermManager permManager;
    @Autowired
    private RoleManager roleManager;
    public List<Role> queryByMemberId(Long memberid) {
        return roleManager.queryByMemberId(memberid);
    }

    public List<Role> queryList() {
        return roleManager.queryList();
    }

    public PageModel<Role> queryPageList(RoleQuery query) {
        PageModel<Role> pageModel= roleManager.queryPageList(query);
        return pageModel;
    }

    public boolean insertRole(Role role, String ids) {
        return roleManager.insertRole(role,ids);
    }

    public boolean updateRole(Role role, String ids) {
        return roleManager.updateRole(role,ids);
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

    public boolean resetadmin() {
        return roleManager.resetadmin();
    }
}
