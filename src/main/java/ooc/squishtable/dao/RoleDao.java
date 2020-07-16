package ooc.squishtable.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class RoleDao extends JdbcDaoSupport implements IRoleDao {

    @Autowired
    public RoleDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Override
    public List<String> getRoleNames(Long userId) {
        String sql = "Select r.Role_Name " //
                + " from USER_ROLE ur, APP_ROLE r " //
                + " where ur.Role_Id = r.Role_Id and ur.User_Id = ? ";

        Object[] params = new Object[]{userId};

        List<String> roles = this.getJdbcTemplate().queryForList(sql, params, String.class);

        return roles;
    }
}
