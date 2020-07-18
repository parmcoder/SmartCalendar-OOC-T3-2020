package ooc.squishtable.main.dao;


import ooc.squishtable.main.model.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    List<AppUser> findByLastName(@Param("lastname") String lastname);

    List<AppUser> findByFirstName(@Param("firstname") String firstname);

}
