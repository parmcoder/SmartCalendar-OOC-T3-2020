package ooc.squishtable.mapper;

import ooc.squishtable.model.AppUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper implements RowMapper<AppUser> {

    public static final String BASE_SQL
            = "SELECT * From APP_USER u ";

    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        AppUser appUser = new AppUser();

        return appUser;
    }
}
