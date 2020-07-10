package ooc.squishtable.dao;

import ooc.squishtable.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class AppUserDao extends JdbcDaoSupport {

    private UserMapper mapper = new UserMapper();

    @Autowired
    public DataSource dataSource;

}
