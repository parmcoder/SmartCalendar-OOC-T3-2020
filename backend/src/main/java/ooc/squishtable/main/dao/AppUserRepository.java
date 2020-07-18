package ooc.squishtable.main.dao;


import ooc.squishtable.main.model.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    List<AppUser> findAppUserBySurname(@Param("surname") String lastname);

    List<AppUser> findAppUserByName(@Param("name") String firstname);

}
