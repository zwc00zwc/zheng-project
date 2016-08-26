package spiservice.system;

import domain.dao.PermDao;
import domain.model.system.Perm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spi.system.PermissionSPI;

import java.util.List;

/**
 * Created by XR on 2016/8/25.
 */
@Service("permissionSPIService")
public class PermissionSPIService implements PermissionSPI {
    @Autowired
    private PermDao permDao;
    public List<Perm> queryList() {
        return permDao.queryList();
    }

    public int insertPerm(Perm perm) {
        return permDao.insertPerm(perm);
    }

    public int updatePerm(Perm perm) {
        return permDao.updateById(perm);
    }

    public Perm queryById(Long id) {
        return permDao.queryById(id);
    }
}
