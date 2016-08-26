package spi.system;

import domain.model.system.Perm;

import java.util.List;

/**
 * Created by XR on 2016/8/25.
 */
public interface PermissionSPI {
    List<Perm> queryList();
    Perm queryById(Long id);
    int insertPerm(Perm perm);
    int updatePerm(Perm perm);
}
