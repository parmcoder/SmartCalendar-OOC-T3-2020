package ooc.squishtable.model;

import java.util.List;

public class AppUser {
    String username;
    String password;
    String taskTable;
    List<String> friends;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTaskTable() {
        return taskTable;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTaskTable(String taskTable) {
        this.taskTable = taskTable;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}
