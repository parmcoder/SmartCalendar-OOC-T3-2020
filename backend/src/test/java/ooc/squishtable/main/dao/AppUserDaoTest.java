package ooc.squishtable.main.dao;

import ooc.squishtable.main.model.AppUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AppUserDaoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppUserDao users;

    private AppUser norbertSiegmund = new AppUser("Norbert", "Siegmund");
    private AppUser jonasHecht = new AppUser("Jonas", "Hecht");

    @Before
    public void fillSomeDataIntoOurDb() {
        // Add new Users to Database
        entityManager.persist(norbertSiegmund);
        entityManager.persist(jonasHecht);
    }

    @Test
    public void testFindByLastName() throws Exception {
        // Search for specific User in Database according to lastname
        List<AppUser> usersWithLastNameSiegmund = users.findByLastName("Siegmund");

        assertThat(usersWithLastNameSiegmund, contains(norbertSiegmund));
    }


    @Test
    public void testFindByFirstName() throws Exception {
        // Search for specific User in Database according to firstname
        List<AppUser> usersWithFirstNameJonas = users.findByFirstName("Jonas");

        assertThat(usersWithFirstNameJonas, contains(jonasHecht));
    }

}