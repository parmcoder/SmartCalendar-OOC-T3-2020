package ooc.squishtable.dao;

import ooc.squishtable.mapper.UserMapper;
import ooc.squishtable.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class AppUserDao extends JdbcDaoSupport implements IAppUserDao {

    private UserMapper mapper = new UserMapper();

    @Autowired
    public DataSource dataSource;

    @Override
    @PostConstruct
    public void init(){
        setDataSource(dataSource);
    }

    @Override
    public AppUser findUserAccount(String userName) {
        String sql = UserMapper.BASE_SQL + " where u.User_Name = ? ";

        Object[] params = new Object[] { userName };
        try {
            AppUser appUserInfo = getJdbcTemplate().queryForObject(sql, params, mapper);
            return appUserInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<AppUser> getAllUsers() {
        String sql = UserMapper.BASE_SQL;
        List<AppUser> resultList;
        try{
            resultList = getJdbcTemplate().query(sql, mapper);

        }catch(EmptyResultDataAccessException e){
            System.out.println("Null!");
            resultList = null;
        }
        return resultList;
    }

    @Override
    public void insertUser(AppUser user, int authority){
        /*
        * find total count and then insert to every table
         */
        String sqlForCount = "select max(USER_ID) from APP_USER";
        String sqlForCount2 = "select max(ID) from USER_ROLE";
        String sqlForInsert1 = "insert into APP_USER (USER_ID, " +
                "USER_NAME, " +
                "ENCRYPTED_PASSWORD, ENABLED, " +
                "USER_REAL_NAME, " +
                "USER_REAL_SURNAME)\n" +
                "values (? , ? , ? , 1, ? ,?)";
        String sqlForInsert2 = "insert into USER_ROLE (ID, USER_ID, ROLE_ID)\n" +
                "values (?, ?, ?)";
        try{
            Long lastId = getJdbcTemplate().queryForObject(sqlForCount, Long.class)+1;
            Long lastRole = getJdbcTemplate().queryForObject(sqlForCount2, Long.class)+1;
            Object[] params = new Object[]{ lastId, user.getUsername(), user.getPassword(),
            user.getName(), user.getSurname()};
            getJdbcTemplate().update(sqlForInsert1, params);

            if(authority>0){
                params = new Object[]{ lastRole, lastId, 1};
                getJdbcTemplate().update(sqlForInsert2, params);
                lastRole++;
            }
            params = new Object[]{ lastRole, lastId, 2};
            getJdbcTemplate().update(sqlForInsert2, params);
        }catch(EmptyResultDataAccessException e){
            System.out.println("Null!");
        }
    }

    @Override
    public void removeUser(AppUser user){
        /*
        * Remove user from both tables
         */

        String sqlForRemove1 = "delete from APP_USER where USER_ID = ?;";
        String sqlForRemove2 = "delete from USER_ROLE where USER_ID = ?;";
        try{
            Object[] params = new Object[]{ user.getId() };
            getJdbcTemplate().update(sqlForRemove2, params);
            getJdbcTemplate().update(sqlForRemove1, params);
        }catch(EmptyResultDataAccessException e){
            System.out.println("Null!");
        }
    }

    @Override
    public void updateUser(AppUser user){
        /*
        * Update user info
         */

        String sqlForUpdate = "update APP_USER set USER_NAME = ?, USER_REAL_NAME = ?, USER_REAL_SURNAME = ? " +
                "where USER_ID = ?";
        try{
            Object[] params = new Object[]{user.getUsername(),
                    user.getName(), user.getSurname(), user.getId() };
            getJdbcTemplate().update(sqlForUpdate, params);
        }catch(EmptyResultDataAccessException e){
            System.out.println("Null!");
        }
    }
}
