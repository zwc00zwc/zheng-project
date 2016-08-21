package domain.model.system;

import domain.model.BaseModel;

/**
 * Created by Administrator on 2016/8/21.
 */
public class RolePerm extends BaseModel {
    private Long id;
    private Long roleId;
    private Long permId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermId() {
        return permId;
    }

    public void setPermId(Long permId) {
        this.permId = permId;
    }
}
