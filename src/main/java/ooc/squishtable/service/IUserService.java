package ooc.squishtable.service;

import ooc.squishtable.model.AppTask;
import ooc.squishtable.model.AppUser;

import java.util.List;

public interface IUserService {
    Boolean addTask(AppTask task, String username);
    Boolean removeTask(AppTask task);
    Boolean updateTask(AppTask task);
    List<AppTask> getAllTasks(String username);
}
