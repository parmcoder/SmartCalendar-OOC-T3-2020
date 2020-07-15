package ooc.squishtable.service;

import ooc.squishtable.dao.AppUserDao;
import ooc.squishtable.dao.TaskDao;
import ooc.squishtable.model.AppTask;
import ooc.squishtable.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private AppUserDao userDao;

    @Override
    public Boolean addTask(AppTask task) {
        taskDao.insertTask(task);
        return true;
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
