package domain.manager;

import domain.mapper.MemberRoleMapper;
import domain.mapper.PermMapper;
import domain.mapper.RoleMapper;
import domain.mapper.RolePermMapper;
import domain.model.system.Role;
import domain.model.system.RolePerm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import common.utility.StringUtility;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by XR on 2016/8/31.
 */
@Transactional
@Component
public class RoleManager {
    @Autowired
    private MemberRoleMapper memberRoleDao;
    @Autowired
    private RoleMapper roleDao;
    @Autowired
    private PermMapper permDao;
    @Autowired
    private RolePermMapper rolePermDao;

    public List<Role> queryList(){
        return roleDao.queryList();
    }
    /**
     * 查询用户的所有角色
     * @param memberid
     * @return
     */
    public List<Role> queryByMemberId(Long memberid){
        List<Role> list=new ArrayList<Role>();
        List<Long> ids=new ArrayList<Long>();
        String rolestr= memberRoleDao.queryRolesByMemberId(memberid);
        ids= StringUtility.StringToListLong(rolestr);
        list=roleDao.queryByIds(ids);
        return list;
    }

    public Role queryById(Long id){
        return roleDao.queryById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean insertRole(Role role,String permids){
        if (roleDao.insertRoleAndReturnId(role)>0){
            RolePerm roleperm=new RolePerm();
            roleperm.setPermIds(permids);
            roleperm.setCreateTime(new Date());
            roleperm.setRoleId(role.getId());
            rolePermDao.insertRolePerm(roleperm);
            return true;
        }
        return false;
    }

    public boolean resetadmin(){
        Role role= roleDao.queryByName("admin");
        if (role!=null){
            List<String> perms= permDao.queryIds();
            String permids= StringUtils.join(perms,",");
            RolePerm rolePerm= rolePermDao.queryByRoleId(role.getId());
            rolePerm.setPermIds(permids);
            rolePerm.setUpdateTime(new Date());
            if (rolePermDao.updateById(rolePerm)>0){
                return true;
            }
        }
        return false;
    }

    public int deleteRole(Long id){
        return roleDao.deleteRole(id);
    }

    public Role queryByName(String rolename){
        Role role=new Role();
        return role;
    }
}
