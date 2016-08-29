package spiservice.system;

import domain.dao.PermDao;
import domain.dao.RoleDao;
import domain.dao.RolePermDao;
import domain.dto.RolePermDto;
import domain.model.system.Perm;
import domain.model.system.Role;
import domain.model.system.RolePerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spi.system.RoleSPI;

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
    public List<Role> queryByMemberId(Long memberid) {
        return roleDao.queryByMemberId(memberid);
    }

    public List<Role> queryList() {
        return roleDao.queryList();
    }

    public boolean insertRole(Role role,String ids) {
        if (roleDao.insertRoleAndReturnId(role)>0){
            rolePermDao.deleteByRoleId(role.getId());
            List<RolePerm> list=new ArrayList<RolePerm>();
            String[] idsstr= ids.split(",");
            for (String id:idsstr) {
                long permid= Long.parseLong(id);
                Perm perm= permDao.queryById(permid);
                if (perm!=null){
                    RolePerm roleperm=new RolePerm();
                    roleperm.setPermId(perm.getId());
                    roleperm.setCreateTime(new Date());
                    roleperm.setRoleId(role.getId());
                    list.add(roleperm);
                }
            }
            if (rolePermDao.insertRolePermByList(list)>0){
                return true;
            }
        }
        return false;
    }

    public RolePermDto queryDtoById(Long id) {
        RolePermDto rolePermDto=new RolePermDto();
        Role role= roleDao.queryById(id);
        rolePermDto.setId(role.getId());
        rolePermDto.setRoleName(role.getRoleName());
        rolePermDto.setDisplayName(role.getDisplayName());
        rolePermDto.setRemark(role.getRemark());
        List<Perm> perms1= permDao.queryByRoleIdAndType(id,1);
        List<Long> ids1=new ArrayList<Long>();
        for (Perm perm:perms1) {
            ids1.add(perm.getId());
        }
        rolePermDto.setPermids1(ids1);
        List<Perm> perms2= permDao.queryByRoleIdAndType(id,2);
        List<Long> ids2=new ArrayList<Long>();
        for (Perm perm:perms2) {
            ids2.add(perm.getId());
        }
        rolePermDto.setPermids2(ids2);
        List<Perm> perms3= permDao.queryByRoleIdAndType(id,3);
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
