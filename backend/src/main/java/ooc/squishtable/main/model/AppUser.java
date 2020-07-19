package ooc.squishtable.main.model;

import javax.persistence.*;

public class AppUser {

    long id;
    String username;
    String password;
    String confirmPassword;
    String name;
    String surname;

    public AppUser() {

    }

    public AppUser(String name, String surname) {
        this.name = name;
        this.surname = surname;
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

    public AppUser(String username, String password, String name, String surname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    public AppUser(String username, String password, String confirmPassword, String name, String surname) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.name = name;
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
