package ooc.squishtable.service;

import ooc.squishtable.model.AppTask;
import ooc.squishtable.model.AppUser;

public interface IUserService {
    Boolean addTask(AppTask task);
    Boolean removeTask(AppTask task);
    Boolean updateTask(AppTask task);
    Boolean getAllTasks(AppUser user);
}
