package spi.system;

import domain.dto.PermLevelDto;
import domain.model.system.Perm;

import java.util.List;

/**
 * Created by XR on 2016/8/25.
 */
public interface PermissionSPI {
    List<Perm> queryList();
    Perm queryById(Long id);
    List<PermLevelDto> queryPermByLevel();
    int insertPerm(Perm perm);
    int updatePerm(Perm perm);
}
