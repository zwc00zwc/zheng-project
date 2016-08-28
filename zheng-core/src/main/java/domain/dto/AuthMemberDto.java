package domain.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by XR on 2016/8/28.
 */
public class AuthMemberDto extends BaseDto {
    private Long id;
    private String phone;
    private String userName;
    private String password;
    private String imgUrl;
    private String address;
    private List<AuthRole> roles;
    private Short status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<AuthRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AuthRole> roles) {
        this.roles = roles;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    private class AuthRole{
        private String name;
        private String url;
        private List<AuthRole> roles;

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

        public List<AuthRole> getRoles() {
            return roles;
        }

        public void setRoles(List<AuthRole> roles) {
            this.roles = roles;
        }
    }
}
