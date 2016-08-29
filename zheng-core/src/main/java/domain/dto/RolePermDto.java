package domain.dto;

import java.util.List;

/**
 * Created by XR on 2016/8/29.
 */
public class RolePermDto extends BaseDto {
    private Long id;
    private String roleName;
    private String displayName;
    private String remark;
    private List<Long> permids1;
    private List<Long> permids2;
    private List<Long> permids3;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Long> getPermids1() {
        return permids1;
    }

    public void setPermids1(List<Long> permids1) {
        this.permids1 = permids1;
    }

    public List<Long> getPermids2() {
        return permids2;
    }

    public void setPermids2(List<Long> permids2) {
        this.permids2 = permids2;
    }

    public List<Long> getPermids3() {
        return permids3;
    }

    public void setPermids3(List<Long> permids3) {
        this.permids3 = permids3;
    }
}
