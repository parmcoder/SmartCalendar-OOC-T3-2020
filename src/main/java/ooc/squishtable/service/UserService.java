package ooc.squishtable.service;

import ooc.squishtable.dao.AppUserDao;
import ooc.squishtable.dao.TaskDao;
import ooc.squishtable.model.AppTask;
import ooc.squishtable.model.AppUser;
import ooc.squishtable.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private TaskDao taskDao;

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
            task.setDateStart(new java.sql.Date(utilDateStart.getTime()));
            utilDateEnd = format.parse(task.getInputDateEnd());
            task.setDateEnd(new java.sql.Date(utilDateEnd.getTime()));

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

    @Override
    public Boolean removeTask(AppTask task) {
        taskDao.removeTask(task);
        return true;
    }

    @Override
    public Boolean updateTask(AppTask task) {
        taskDao.updateTask(task);
        return true;
    }

    @Override
    public List<AppTask> getAllTasks(String username) {
        AppUser user = userDao.findUserAccount(username);
        return taskDao.findAllUserTasks(user.getId());
    }
}
