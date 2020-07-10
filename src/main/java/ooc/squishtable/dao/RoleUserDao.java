package ooc.squishtable.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class RoleUserDao extends JdbcDaoSupport {

    @Autowired
    public RoleUserDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<String> getRoleNames(Long userId) {
        String sql = "Select r.Role_Name " //
                + " from USER_ROLE ur, APP_ROLE r " //
                + " where ur.Role_Id = r.Role_Id and ur.User_Id = ? ";

        Object[] params = new Object[]{userId};

        List<String> roles = this.getJdbcTemplate().queryForList(sql, params, String.class);

        return roles;
    }
}
