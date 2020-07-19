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

    @Override
    public Boolean addTask(AppTask task, String username) {
        Boolean success = false;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        AppUser user = userDao.findUserAccount(username);
        task.setUid(user.getId());
        java.util.Date utilDateStart, utilDateEnd;
        try {
            utilDateStart = format.parse(task.getInputDateStart());
            task.setDateStart(new java.sql.Date(utilDateStart.getTime()));     // Set start date
            utilDateEnd = format.parse(task.getInputDateEnd());
            task.setDateEnd(new java.sql.Date(utilDateEnd.getTime()));         // Set end date

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
            if(success) taskDao.insertTask(task);       // Insert user's task
        }

        return success;
    }

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

    @Override
    public List<AppTask> getAllTasks(String username) {
        AppUser user = userDao.findUserAccount(username);
        return taskDao.findAllUserTasks(user.getId());
    }
}
