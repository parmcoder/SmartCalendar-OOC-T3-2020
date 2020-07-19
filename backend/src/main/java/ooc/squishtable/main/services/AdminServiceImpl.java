package ooc.squishtable.main.services;

import ooc.squishtable.main.dao.AppUserDao;
import ooc.squishtable.main.model.AppUser;
import ooc.squishtable.main.utilities.EncryptorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AppUserDao appUserDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<AppUser> findAll() {
        return this.appUserDao.getAllUsers();
    }

    @Override
    public AppUser getUser(String username) {
        return this.appUserDao.findUserAccount(username);
    }

    /**
     * Add new user to database
     */
    @Override
    public Boolean addNewUser(AppUser user) {
        /**
         * Check whether user already exists or not
         */
        if(checkExistedUser(user)){
            return false;
        }
        user.setPassword(EncryptorUtils.encryptPassword(user.getPassword()));
        this.appUserDao.insertUser(user,0);
        return true;
    }

    /**
     * Add new admin
     * @param user
     * @return true if success, else otherwise
     */
    @Override
    public Boolean addNewAdmin(AppUser user) {
        if(checkExistedUser(user)){
            return false;
        }
        user.setPassword(EncryptorUtils.encryptPassword(user.getPassword()));
        this.appUserDao.insertUser(user,1);
        return true;
    }

    /**
     * Update user information
     * @param userName
     * @param user
     * @return true if update successfully, false otherwise
     */
    @Override
    public Boolean updateUserInfo(String userName, AppUser user) {
        Boolean result = true;
        AppUser appUser = this.appUserDao.findUserAccount(userName);
        if(!user.getUsername().isEmpty() && !checkExistedUser(user)){    // Check the existence of user
            appUser.setUsername(user.getUsername());
        }
        else{           // User does not exist
            result = false;
        }
        if(!user.getName().isEmpty()) appUser.setName(user.getName());
        if(!user.getSurname().isEmpty()) appUser.setSurname(user.getSurname());
        if(passwordEncoder.matches(user.getConfirmPassword(), appUser.getPassword())) this.appUserDao.updateUser(appUser);
        return result;
    }

    /**
     * Get username information
     * @param username
     * @return the information of this user
     */
    @Override
    public AppUser getCurrentInfo(String username) {
        return this.appUserDao.findUserAccount(username);
    }

    /**
     * Check whether password and confirm password are equal or not
     * @param user
     * @return true if password is matched
     */
    @Override
    public Boolean checkMatching(AppUser user) {
        return passwordEncoder.matches(user.getConfirmPassword(), passwordEncoder.encode(user.getPassword()));
    }

    /**
     * Remove user from the database
     * @param user
     * @return true if user exists, and delete. Otherwise, false
     */
    @Override
    public Boolean removeUser(AppUser user) {
        System.out.println(user);

        if(checkExistedUser(user)){
            this.appUserDao.removeUser(user);
            return true;
        }
        return false;
    }

    /**
     * Check the existence of this user
     * @param user
     * @return true if user exists, false otherwise
     */
    @Override
    public Boolean checkExistedUser(AppUser user){
        if(this.appUserDao.findUserAccount(user.getUsername()) != null){
            return true;
        }
        return false;
    }
}
