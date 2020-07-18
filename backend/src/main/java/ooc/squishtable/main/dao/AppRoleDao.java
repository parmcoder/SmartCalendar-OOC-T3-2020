package ooc.squishtable.main.dao;

import java.util.List;

public interface AppRoleDao {
    List<String> getRoleNames(Long userId);
}
