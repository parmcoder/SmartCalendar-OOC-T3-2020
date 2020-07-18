package ooc.squishtable.main.dao;

import ooc.squishtable.main.model.AppTask;

import javax.annotation.PostConstruct;
import java.util.List;

public interface AppTaskDao {
    @PostConstruct
    void init();

    List<AppTask> findAllUserTasks(long uid);

    AppTask findTask(long tid);

    void insertTask(AppTask task);

    void removeTask(AppTask task);

    void updateTask(AppTask task);
}
