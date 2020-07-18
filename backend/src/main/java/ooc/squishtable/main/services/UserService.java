package ooc.squishtable.main.services;

import ooc.squishtable.main.model.AppTask;

import java.util.List;

public interface UserService {

    Boolean addTask(AppTask task, String username);
    Boolean removeTask(AppTask task);
    Boolean updateTask(AppTask task, long tid);

    List<AppTask> getAllTasks(String username);
}
