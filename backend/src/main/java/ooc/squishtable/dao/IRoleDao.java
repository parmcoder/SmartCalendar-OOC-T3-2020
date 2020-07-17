package ooc.squishtable.dao;

import java.util.List;

public interface IRoleDao {
    List<String> getRoleNames(Long userId);
}
