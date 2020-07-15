package ooc.squishtable.service;

import ooc.squishtable.dao.AppUserDao;
import ooc.squishtable.dao.TaskDao;
import ooc.squishtable.model.AppTask;
import ooc.squishtable.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private TaskDao taskDao;

    @Override
    public Boolean addTask(AppTask task) {
        return null;
    }

    @Override
    public Boolean removeTask(AppTask task) {
        return null;
    }

    @Override
    public Boolean updateTask(AppTask task) {
        return null;
    }

    @Override
    public Boolean getAllTasks(AppUser user) {
        return null;
    }
}
