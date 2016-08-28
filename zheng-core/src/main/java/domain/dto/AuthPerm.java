package domain.dto;

import java.util.List;

/**
 * Created by XR on 2016/8/28.
 */
public class AuthPerm extends BaseDto {
    private String name;
    private String url;
    private List<AuthPerm> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<AuthPerm> getRoles() {
        return roles;
    }

    public void setRoles(List<AuthPerm> roles) {
        this.roles = roles;
    }
}
