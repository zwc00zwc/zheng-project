package domain.dao;

import domain.model.system.Perm;

import java.util.List;
import java.util.Map;

/**
 * Created by XR on 2016/8/22.
 */
public interface PermDao {
    List<Perm> queryList();

    Perm queryById(Long id);

    List<Perm> queryByParentId(Long id);

    List<Perm> queryByType(List<Integer> types);

    int insertPerm(Perm perm);

    int updateById(Perm perm);
}
