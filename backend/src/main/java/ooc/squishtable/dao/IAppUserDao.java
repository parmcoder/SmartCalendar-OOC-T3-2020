package ooc.squishtable.dao;

import ooc.squishtable.model.AppUser;

import javax.annotation.PostConstruct;
import java.util.List;

public interface IAppUserDao {
    @PostConstruct
    void init();

    AppUser findUserAccount(String userName);

    List<AppUser> getAllUsers();

    void insertUser(AppUser user, int authority);

    void removeUser(AppUser user);

    void updateUser(AppUser user);
}
