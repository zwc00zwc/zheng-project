package spi.system;

import domain.dto.PermLevelDto;
import domain.model.PageModel;
import domain.model.system.Perm;
import domain.model.system.query.PermQuery;

import java.util.List;
import java.util.Set;

/**
 * Created by XR on 2016/8/25.
 */
public interface PermissionSPI {
    PageModel<Perm> queryPageList(PermQuery query);
    Perm queryById(Long id);
    List<PermLevelDto> queryPermByLevel();
    List<Perm> queryByType(List<Integer> types);

    Set<String> queryUrlsByMemberId(Long id);
    int insertPerm(Perm perm);
    int updatePerm(Perm perm);

    int deletePerm(Long id);
}
