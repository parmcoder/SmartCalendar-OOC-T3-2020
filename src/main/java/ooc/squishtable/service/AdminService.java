package ooc.squishtable.service;

import ooc.squishtable.dao.AppUserDao;
import ooc.squishtable.model.AppUser;
import ooc.squishtable.utils.EncryptorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private AppUserDao appUserDao;

    @Override
    public List<AppUser> findAll() {
        return this.appUserDao.getAllUsers();
    }

    @Override
    public Boolean addNewUser(AppUser user) {
        if(checkExistedUser(user)){
            return false;
        }
        user.setPassword(EncryptorUtils.encryptPassword(user.getPassword()));
        this.appUserDao.insertUser(user,0);
        return true;
    }

    @Override
    public Boolean addNewAdmin(AppUser user) {
        if(checkExistedUser(user)){
            return false;
        }
        user.setPassword(EncryptorUtils.encryptPassword(user.getPassword()));
        this.appUserDao.insertUser(user,1);
        return true;    }

    @Override
    public Boolean updateUserInfo(String userName, AppUser user) {
        Boolean result = true;
        AppUser appUser = this.appUserDao.findUserAccount(userName);
        if(!user.getUsername().isEmpty() && !checkExistedUser(user)){
            appUser.setUsername(user.getUsername());
        }else{
            result = false;
        }
        return result;
    }

    @Override
    public AppUser getCurrentInfo(String username) {
        return this.appUserDao.findUserAccount(username);
    }

    @Override
    public Boolean checkMatching(AppUser user) {
        return user.getPassword().contentEquals(user.getConfirmPassword());
    }

    @Override
    public Boolean removeUser(AppUser user) {
        if(checkExistedUser(user)){
            this.appUserDao.removeUser(user);
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkExistedUser(AppUser user){
        if(this.appUserDao.findUserAccount(user.getUsername()) != null){
            return true;
        }
        return false;
    }
}
