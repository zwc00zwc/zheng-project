package domain.dao;

import domain.model.system.Perm;

/**
 * Created by XR on 2016/8/22.
 */
public interface PermDao {
    Perm queryById(Long id);

    int insertPerm(Perm perm);

    int updateById(Perm perm);
}
