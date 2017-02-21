package spi.system;

import domain.dto.RolePermDto;
import domain.model.PageModel;
import domain.model.system.Role;
import domain.model.system.query.RoleQuery;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by XR on 2016/8/25.
 */
public interface RoleSPI {
    List<Role> queryByMemberId(Long memberid);

    List<Role> queryList();

    PageModel<Role> queryPageList(RoleQuery query);

    boolean insertRole(Role role,String ids);

    boolean updateRole(Role role,String ids);

    RolePermDto queryDtoById(Long id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    int deleteRole(Long id);

    boolean resetadmin();
}
