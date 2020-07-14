package ooc.squishtable.dao;

import ooc.squishtable.mapper.TaskMapper;
import ooc.squishtable.mapper.UserMapper;
import ooc.squishtable.model.AppTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class TaskUserDao extends JdbcDaoSupport {

    private TaskMapper mapper = new TaskMapper();

    @Autowired
    public DataSource dataSource;

    /*
    ! Make sure this method can retrieve all tasks
     */
    public List<AppTask> findAllUserTasks(long uid) {
        String sql = TaskMapper.BASE_SQL + " where u.User_Name = ? ";

        Object[] params = new Object[] { uid };
        try {
            List<AppTask> appUserInfo = getJdbcTemplate().query(sql, params, mapper);
            return appUserInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
//
//    public List<AppUser> getAllUsers() {
//        String sql = UserMapper.BASE_SQL;
//        List<AppUser> resultList;
//        try{
//            resultList = getJdbcTemplate().query(sql, mapper);
//
//        }catch(EmptyResultDataAccessException e){
//            System.out.println("Null!");
//            resultList = null;
//        }
//        return resultList;
//    }
//
//    public void insertUser(AppUser user, int authority){
//        /*
//        ? find total count and then insert to every table
//         */
//        String sqlForCount = "select max(USER_ID) from APP_USER";
//        String sqlForCount2 = "select max(ID) from USER_ROLE";
//        String sqlForInsert1 = "insert into APP_USER (USER_ID, USER_NAME, ENCRYPTED_PASSWORD, ENABLED)\n" +
//                "values (?\n" +
//                "        , ?\n" +
//                "        , ?\n" +
//                "        , 1)";
//        String sqlForInsert2 = "insert into USER_ROLE (ID, USER_ID, ROLE_ID)\n" +
//                "values (?, ?, ?)";
//        try{
//            Long lastId = getJdbcTemplate().queryForObject(sqlForCount, Long.class)+1;
//            Long lastRole = getJdbcTemplate().queryForObject(sqlForCount2, Long.class)+1;
//            Object[] params = new Object[]{ lastId, user.getUserName(), user.getEncryptedPassword() };
//            getJdbcTemplate().update(sqlForInsert1, params);
//
//            if(authority>0){
//                params = new Object[]{ lastRole, lastId, 1};
//                getJdbcTemplate().update(sqlForInsert2, params);
//            }
//            params = new Object[]{ lastRole, lastId, 2};
//            getJdbcTemplate().update(sqlForInsert2, params);
//        }catch(EmptyResultDataAccessException e){
//            System.out.println("Null!");
//        }
//    }
//
//    public void removeUser(AppUser user){
//        /*
//        ? Remove user from both tables
//         */
//
//        String sqlForRemove1 = "delete from APP_USER where USER_ID = ?;";
//        String sqlForRemove2 = "delete from USER_ROLE where USER_ID = ?;";
//        try{
//            Object[] params = new Object[]{ user.getUserId() };
//            getJdbcTemplate().update(sqlForRemove2, params);
//            getJdbcTemplate().update(sqlForRemove1, params);
//        }catch(EmptyResultDataAccessException e){
//            System.out.println("Null!");
//        }
//    }
//
//    public void updateUser(AppUser user){
//        /*
//        ? Update
//         */
//
//        String sqlForUpdate = "update APP_USER set USER_NAME = ?, USER_STATUS = ? where USER_ID = ?";
////        String sqlForRemove2 = "delete from USER_ROLE where USER_ID = ?;";
//        try{
//            Object[] params = new Object[]{ user.getUserName(), user.getStatus()  ,user.getUserId() };
//            getJdbcTemplate().update(sqlForUpdate, params);
////            getJdbcTemplate().update(sqlForRemove1, params);
//        }catch(EmptyResultDataAccessException e){
//            System.out.println("Null!");
//        }
//    }

}
