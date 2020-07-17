package ooc.squishtable.mapper;

import ooc.squishtable.model.AppUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<AppUser> {

    public static final String BASE_SQL
            = "SELECT * From APP_USER u ";

    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        AppUser appUser = new AppUser();
        appUser.setId( rs.getLong("USER_ID"));
        appUser.setUsername(rs.getString("USER_NAME"));
        appUser.setPassword(rs.getString("ENCRYPTED_PASSWORD"));
        appUser.setName(rs.getString("USER_REAL_NAME"));
        appUser.setSurname(rs.getString("USER_REAL_SURNAME"));

        return appUser;
    }
}
