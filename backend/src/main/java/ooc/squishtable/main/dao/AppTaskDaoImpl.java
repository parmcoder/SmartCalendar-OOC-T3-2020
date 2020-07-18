package ooc.squishtable.main.dao;

import ooc.squishtable.main.mapper.TaskMapper;
import ooc.squishtable.main.model.AppTask;

import javax.activation.DataSource;
import javax.annotation.PostConstruct;
import java.util.List;

@Repository
@Transactional
public class AppTaskDaoImpl extends JdbcDaoSupport implements AppTaskDao {

    private TaskMapper mapper = new TaskMapper();

    @Autowired
    public DataSource dataSource;

    @Override
    @PostConstruct
    public void init(){
        setDataSource(dataSource);
    }

    @Override
    public List<AppTask> findAllUserTasks(long uid) {
        String sql = TaskMapper.BASE_SQL + " where u.UID = ? ";

        Object[] params = new Object[] { uid };
        try {
            List<AppTask> tasks = getJdbcTemplate().query(sql, params, mapper);
            return tasks;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public AppTask findTask(long tid) {
        String sql = TaskMapper.BASE_SQL + " where u.TID = ? ";

        Object[] params = new Object[] { tid };
        try {
            AppTask task = getJdbcTemplate().queryForObject(sql, params, mapper);
            return task;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void insertTask(AppTask task){
        /*
        ? find total count and then insert to every table
         */
        String sqlForInsert1 = "insert into APP_TASKS(TITLE, DESCRIPTION, START_DATE, END_DATE, UID)\n" +
                "    value (?,?,?,?,?);";
        try{
            Object[] params = new Object[]{ task.getTitle(),task.getDescription(),
                    task.getDateStart(), task.getDateEnd(), task.getUid() };
            getJdbcTemplate().update(sqlForInsert1, params);
        }catch(EmptyResultDataAccessException e){
            System.out.println("Null!");
        }
    }

    @Override
    public void removeTask(AppTask task){
        /*
        ? find total count and then insert to every table
         */
        String sqlForInsert1 = "delete from APP_TASKS where TID = ?";
        try{
            Object[] params = new Object[]{ task.getTid() };
            getJdbcTemplate().update(sqlForInsert1, params);
        }catch(EmptyResultDataAccessException e){
            System.out.println("Null!");
        }
    }

    @Override
    public void updateTask(AppTask task){
        /*
        ? Update
        */

        String sqlForUpdate = "update APP_TASKS" +
                "set TITLE = ?, DESCRIPTION = ?, START_DATE = ?, END_DATE = ? " +
                "where tid = ?";
        try{
            Object[] params = new Object[]{ task.getTitle(),task.getDescription(),
                    task.getDateStart(), task.getDateEnd() };
            getJdbcTemplate().update(sqlForUpdate, params);
//          getJdbcTemplate().update(sqlForRemove1, params);
        }catch(EmptyResultDataAccessException e){
            System.out.println("Null!");
        }
    }
}
