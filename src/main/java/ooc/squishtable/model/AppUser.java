package ooc.squishtable.model;

import java.util.List;

public class AppUser {
    String username;
    String password;
    String confirmPassword;
    long id;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(long id) {
        this.id = id;
    }
}
