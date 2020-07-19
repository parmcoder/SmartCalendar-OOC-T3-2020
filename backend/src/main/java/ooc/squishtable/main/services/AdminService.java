package ooc.squishtable.main.services;

import ooc.squishtable.main.model.AppUser;

import java.util.List;

public interface AdminService {

    List<AppUser> findAll();

    AppUser getUser(String username);

    Boolean removeUser(AppUser user);

    Boolean checkExistedUser(AppUser user);

    Boolean addNewUser(AppUser user);

    Boolean addNewAdmin(AppUser user);

    Boolean updateUserInfo(String userName, AppUser user);

    AppUser getCurrentInfo(String username);

    Boolean checkMatching(AppUser user);
}
