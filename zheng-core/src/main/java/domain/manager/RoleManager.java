package domain.manager;

import domain.dao.MemberRoleDao;
import domain.dao.RoleDao;
import domain.dao.RolePermDao;
import domain.model.system.Role;
import domain.model.system.RolePerm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utility.StringUtility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by XR on 2016/8/31.
 */
@Component
public class RoleManager {
    @Autowired
    private MemberRoleDao memberRoleDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RolePermDao rolePermDao;

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

    public int deleteRole(Long id){
        return roleDao.deleteRole(id);
    }
}
