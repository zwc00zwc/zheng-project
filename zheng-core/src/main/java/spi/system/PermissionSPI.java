package spi.system;

import domain.model.system.Perm;

/**
 * Created by XR on 2016/8/25.
 */
public interface PermissionSPI {
    int insertPerm(Perm perm);
    int updatePerm(Perm perm);
}
