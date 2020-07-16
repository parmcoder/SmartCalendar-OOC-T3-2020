package ooc.squishtable.dao;

import ooc.squishtable.model.AppTask;

import javax.annotation.PostConstruct;
import java.util.List;

public interface ITaskDao {
    @PostConstruct
    void init();

    List<AppTask> findAllUserTasks(long uid);

    void insertTask(AppTask task);

    void removeTask(AppTask task);

    void updateTask(AppTask task);
}
