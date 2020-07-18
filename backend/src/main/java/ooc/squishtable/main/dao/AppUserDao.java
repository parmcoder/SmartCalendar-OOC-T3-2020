package ooc.squishtable.main.dao;

import ooc.squishtable.main.model.AppUser;

import javax.annotation.PostConstruct;
import java.util.List;

public interface AppUserDao {
    @PostConstruct
    void init();

    AppUser findUserAccount(String userName);

    List<AppUser> getAllUsers();

    void insertUser(AppUser user, int authority);

    void removeUser(AppUser user);

    void updateUser(AppUser user);
}
