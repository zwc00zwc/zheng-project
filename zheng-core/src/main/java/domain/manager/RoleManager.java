package domain.manager;

import domain.mapper.MemberRoleMapper;
import domain.mapper.PermMapper;
import domain.mapper.RoleMapper;
import domain.mapper.RolePermMapper;
import domain.model.PageModel;
import domain.model.system.Role;
import domain.model.system.RolePerm;
import domain.model.system.query.RoleQuery;
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
@Component
public class RoleManager {
    @Autowired
    private MemberRoleMapper memberRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermMapper permMapper;
    @Autowired
    private RolePermMapper rolePermMapper;

    public List<Role> queryList(){
        return roleMapper.queryList();
    }

    public PageModel<Role> queryPageList(RoleQuery query){
        List<Role> list=roleMapper.queryPageList(query);
        int count=roleMapper.queryCountPage(query);
        PageModel<Role> pageModel=new PageModel<Role>(list,query.getCurrPage(),count,query.getPageSize());
        return pageModel;
    }

    /**
     * 查询用户的所有角色
     * @param memberid
     * @return
     */
    public List<Role> queryByMemberId(Long memberid){
        List<Role> list=new ArrayList<Role>();
        List<Long> ids=new ArrayList<Long>();
        String rolestr= memberRoleMapper.queryRolesByMemberId(memberid);
        ids= StringUtility.StringToListLong(rolestr);
        list=roleMapper.queryByIds(ids);
        return list;
    }

    public Role queryById(Long id){
        return roleMapper.queryById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean insertRole(Role role,String permids){
        if (roleMapper.insertRoleAndReturnId(role)>0){
            RolePerm roleperm=new RolePerm();
            roleperm.setPermIds(permids);
            roleperm.setCreateTime(new Date());
            roleperm.setRoleId(role.getId());
            rolePermMapper.insertRolePerm(roleperm);
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(Role role,String permids){
        RolePerm roleperm=new RolePerm();
        roleperm.setPermIds(permids);
        roleperm.setCreateTime(new Date());
        roleperm.setRoleId(role.getId());
        if (rolePermMapper.updateByRoleId(roleperm)>0){
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean resetadmin(){
        Role role= roleMapper.queryByName("admin");
        if (role!=null){
            List<String> perms= permMapper.queryIds();
            String permids= StringUtils.join(perms,",");
            RolePerm rolePerm= rolePermMapper.queryByRoleId(role.getId());
            rolePerm.setPermIds(permids);
            rolePerm.setUpdateTime(new Date());
            if (rolePermMapper.updateById(rolePerm)>0){
                return true;
            }
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteRole(Long id){
        return roleMapper.deleteRole(id);
    }

    public Role queryByName(String rolename){
        Role role=new Role();
        return role;
    }
}
