package ooc.squishtable.main.mapper;

import ooc.squishtable.main.model.AppTask;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper implements RowMapper<AppTask> {

    public static final String BASE_SQL
            = "SELECT * From APP_TASKS u ";

    @Override
    public AppTask mapRow(ResultSet rs, int rowNum) throws SQLException {
        AppTask appTask = new AppTask();
        appTask.setUid(rs.getLong("UID"));
        appTask.setTid(rs.getLong("TID"));
        appTask.setTitle(rs.getString("TITLE"));
        appTask.setDescription(rs.getString("DESCRIPTION"));
        /*
        ! Still need to test how this work
        */
        appTask.setDateStart(rs.getDate("START_DATE"));
        appTask.setDateEnd(rs.getDate("END_DATE"));

        return appTask;
    }
}
