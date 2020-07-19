package ooc.squishtable.main.model;

import java.util.List;

public class AppJwt {

    private String type;
    private String token;
    private List<String> roles;
    private AppUser userInfo = new AppUser();

    public AppJwt(String accessToken, Long id, String username, List<String> roles) {
        this.token = accessToken;
        this.userInfo.setId(id);
        this.userInfo.setUsername(username);
        this.roles = roles;
        this.type = "Bearer";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public AppUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(AppUser userInfo) {
        this.userInfo = userInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
