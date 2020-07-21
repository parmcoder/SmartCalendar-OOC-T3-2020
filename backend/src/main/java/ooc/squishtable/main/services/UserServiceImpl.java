package ooc.squishtable.main.services;

import ooc.squishtable.main.dao.AppTaskDao;
import ooc.squishtable.main.dao.AppUserDao;
import ooc.squishtable.main.model.AppTask;
import ooc.squishtable.main.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AppTaskDao taskDao;

    @Autowired
    private AppUserDao userDao;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private java.util.Date utilDateStart, utilDateEnd;

    /**
     * Add user's task
     * @param task
     * @param username
     * @return true if adding task successfully, otherwise false
     */
    @Override
    public Boolean addTask(AppTask task, String username) {
        Boolean success = false;
        AppUser user = this.userDao.findUserAccount(username);
        task.setUid(user.getId());
        try {
            taskDateSetter(task);

            // End date need to be before start date
            if (utilDateEnd.after(utilDateStart)) {
                success = true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            if (success) this.taskDao.insertTask(task);
        }

        return success;
    }

    /**
     * Remove user's task
     *
     * @param task
     * @return true if task is removed successfully, otherwise false
     */
    @Override
    public Boolean removeTask(AppTask task) {
        if (this.taskDao.findTask(task.getTid()) != null) taskDao.removeTask(task);
        else return false;
        return true;
    }

    /*
    ? Must observe how user will edit the task
     */
    @Override
    public Boolean updateTask(AppTask task, long tid) {
        Boolean success = false;
        AppTask old = this.taskDao.findTask(tid);
        if (old == null) return false;
        try {
            taskDateSetter(task);
            old.setDateStart(task.getDateStart());
            old.setDateEnd(task.getDateEnd());
            old.setDescription(task.getDescription());
            old.setTitle(task.getTitle());
            // End date need to be before start date
            if (utilDateEnd.after(utilDateStart)) {
                success = true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            if (success) this.taskDao.updateTask(old);
        }
        return success;
    }

    @Override
    public void taskDateSetter(AppTask task) throws ParseException {
        utilDateStart = format.parse(task.getInputDateStart().replace('T', ' ').substring(0, 24));
        task.setDateStart(new Timestamp(utilDateStart.getTime()));
        utilDateEnd = format.parse(task.getInputDateEnd().replace('T', ' ').substring(0, 24));
        task.setDateEnd(new Timestamp(utilDateEnd.getTime()));
    }

    /**
     * Get all user's tasks
     *
     * @param username
     * @return All user's tasks
     */
    @Override
    public List<AppTask> getAllTasks(String username) {
        AppUser user = this.userDao.findUserAccount(username);
        return this.taskDao.findAllUserTasks(user.getId());
    }
}
