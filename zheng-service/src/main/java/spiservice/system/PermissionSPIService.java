package spiservice.system;

import domain.model.system.Perm;
import org.springframework.stereotype.Service;
import spi.system.PermissionSPI;

/**
 * Created by XR on 2016/8/25.
 */
@Service("permissionSPIService")
public class PermissionSPIService implements PermissionSPI {
    public int insertPerm(Perm perm) {
        return 0;
    }

    public int updatePerm(Perm perm) {
        return 0;
    }
}
