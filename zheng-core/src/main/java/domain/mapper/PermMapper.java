package domain.mapper;

import domain.model.system.Perm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by XR on 2016/8/22.
 */
public interface PermMapper {
    List<Perm> queryList();

    Perm queryById(Long id);

    List<Perm> queryByParentId(Long id);

    List<Perm> queryByType(List<Integer> types);

    List<Perm> queryByIdsAndParentId(@Param("ids") List<Long> ids,@Param("parentid") Long par);

    List<String> queryUrlByListId(List<Long> ids);

    List<String> queryIds();

    List<Perm> queryByIdsAndType(@Param("ids") List<Long> ids,@Param("type") Integer type);

    int insertPerm(Perm perm);

    int updateById(Perm perm);

    int deleteById(Long id);
}
