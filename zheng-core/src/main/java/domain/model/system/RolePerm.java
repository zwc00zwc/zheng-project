package domain.model.system;

import domain.model.BaseModel;

import java.util.Date;

/**
 * Created by Administrator on 2016/8/21.
 */
public class RolePerm extends BaseModel {
    private Long id;
    private Long roleId;
    private String permIds;
    private Date createTime;
    private Date updateTime;

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

    public String getPermIds() {
        return permIds;
    }

    public void setPermIds(String permIds) {
        this.permIds = permIds;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
