package ooc.squishtable.main.services;

import ooc.squishtable.main.dao.AppTaskDao;
import ooc.squishtable.main.dao.AppUserDao;
import ooc.squishtable.main.model.AppTask;
import ooc.squishtable.main.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AppTaskDao taskDao;

    @Autowired
    private AppUserDao userDao;

    /**
     * Add user's task
     * @param task
     * @param username
     * @return true if adding task successfully, otherwise false
     */
    @Override
    public Boolean addTask(AppTask task, String username) {
        Boolean success = false;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        AppUser user = userDao.findUserAccount(username);
        task.setUid(user.getId());
        java.util.Date utilDateStart, utilDateEnd;
        try {
            utilDateStart = format.parse(task.getInputDateStart());
            task.setDateStart(new java.sql.Date(utilDateStart.getTime()));
            utilDateEnd = format.parse(task.getInputDateEnd());
            task.setDateEnd(new java.sql.Date(utilDateEnd.getTime()));

            // End date need to be before start date
            if(utilDateEnd.after(utilDateStart)){
                success = true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        finally {
            if(success) taskDao.insertTask(task);
        }

        return success;
    }

    /**
     * Remove user's task
     * @param task
     * @return true if task is removed successfully, otherwise false
     */
    @Override
    public Boolean removeTask(AppTask task) {
        if(taskDao.findTask(task.getTid()) != null) taskDao.removeTask(task);
        else return false;
        return true;
    }

    /*
    ? Must observe how user will edit the task
     */
    @Override
    public Boolean updateTask(AppTask task, long tid) {
        AppTask old = taskDao.findTask(tid);

        taskDao.updateTask(task);
        return true;
    }

    /**
     * Get all user's tasks
     * @param username
     * @return All user's tasks
     */
    @Override
    public List<AppTask> getAllTasks(String username) {
        AppUser user = userDao.findUserAccount(username);
        return taskDao.findAllUserTasks(user.getId());
    }
}
