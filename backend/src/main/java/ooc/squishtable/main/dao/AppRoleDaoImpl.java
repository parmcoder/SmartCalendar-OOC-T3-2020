package ooc.squishtable.main.dao;

import javax.activation.DataSource;
import java.util.List;

@Repository
@Transactional
public class AppRoleDaoImpl extends JdbcDaoSupport implements AppRoleDao {
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
