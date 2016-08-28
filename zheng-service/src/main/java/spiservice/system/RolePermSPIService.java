package spiservice.system;

import domain.dao.PermDao;
import domain.dao.RolePermDao;
import domain.model.system.Perm;
import domain.model.system.RolePerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spi.system.RolePermSPI;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by XR on 2016/8/28.
 */
@Transactional
@Service("rolePermSPIService")
public class RolePermSPIService implements RolePermSPI {
    @Autowired
    private RolePermDao rolePermDao;

    @Autowired
    private PermDao permDao;
    public int insertRolePermByList(String ids,Long roleid) {
        rolePermDao.deleteByRoleId(roleid);
        List<RolePerm> list=new ArrayList<RolePerm>();
        String[] idsstr= ids.split(",");
        for (String id:idsstr) {
            long permid= Long.parseLong(id);
            Perm perm= permDao.queryById(permid);
            if (perm!=null){
                RolePerm roleperm=new RolePerm();
                roleperm.setPermId(perm.getId());
                roleperm.setCreateTime(new Date());
                roleperm.setRoleId(roleid);
                list.add(roleperm);
            }
        }
        int i= rolePermDao.insertRolePermByList(list);
        return i;
    }

    public int deleteByRoleId(Long roleid) {
        return rolePermDao.deleteByRoleId(roleid);
    }
}
