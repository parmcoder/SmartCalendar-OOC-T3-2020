package ooc.squishtable.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "APP_USER")
public class AppUser {
    String username;
    String password;
    String confirmPassword;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    public AppUser() {

    }

    public AppUser(Long userId, String userName) {
        this.id = userId;
        this.username = userName;
    }

    public AppUser(Long userId, String userName, String encryptedPassword) {
        this.id = userId;
        this.username = userName;
        this.password = encryptedPassword;
    }

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

    @Override
    public String toString() {
        return this.username + "/" + this.password;
    }

}
