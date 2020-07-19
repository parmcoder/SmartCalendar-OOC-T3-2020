package ooc.squishtable.main.controller;

import ooc.squishtable.main.model.AppTask;
import ooc.squishtable.main.model.AppUser;
import ooc.squishtable.main.services.AdminService;
import ooc.squishtable.main.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController()
@RequestMapping("/api")
public class BackendController {

    private static final Logger LOG = LoggerFactory.getLogger(BackendController.class);

    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";
    public static final String SECURED_TEXT = "Hello from the secured resource!";

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/hello")
    public String sayHello() {
        LOG.info("GET called on /hello resource");
        return HELLO_TEXT;
    }

    @GetMapping(path = "/user/{username}")
    public AppUser getUserByUsername(@PathVariable("username") String username) {

        return adminService.getUser(username);
    }

    @PostMapping(path = "/user/{username}/{password}/{name}/{surname}")
    public ResponseEntity registerNewUser(@PathVariable("username") String username, @PathVariable("password") String password,
                                          @PathVariable("name") String name, @PathVariable("surname") String surname) {
        AppUser creating = new AppUser(username, password, name, surname);
        if (adminService.addNewUser(creating)) return ResponseEntity.status(HttpStatus.CREATED).body(adminService.getUser(creating.getUsername()));
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping(path = "/user/{title}/{description}/{dateStart}/{dateEnd}")
    public ResponseEntity addTask(@PathVariable("title") String title, @PathVariable("description") String description,
                                          @PathVariable("dateStart") String dateStart, @PathVariable("dateEnd") String dateEnd,
                                  Principal principal) {
        User loggedInUser = (User) ((Authentication) principal).getPrincipal();
        AppTask creating = new AppTask(title, description, dateStart, dateEnd);
        if (userService.addTask(creating,loggedInUser.getUsername()))
            return ResponseEntity.status(HttpStatus.CREATED).body(creating);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    /*
    TODO: Get a good task list to display from username
    */

    /*
    TODO: Update task on task id
    */

    /*
    TODO: Remove task on task id
    */

    /*
    TODO: Edit user info
    */

    /*
    TODO: Get a good user list to display (ADMIN & optional)
    */

    /*
    TODO: Remove user using username or id (ADMIN & optional)
    */



    /*
    ? The security part needed to be configured by frontend (vuex)
     */
    @GetMapping(path = "/secured")
    public @ResponseBody String getSecured() {
        LOG.info("GET successfully called on /secured resource");
        return SECURED_TEXT;
    }

    @RequestMapping(path = "/admin/hello", method = RequestMethod.GET)
    public @ResponseBody
    String getAdminHello() {
        LOG.info("Admin?");
        return "Admin: " + SECURED_TEXT;
    }



    /*
    ! These are not what we are working on
     */
//    @RequestMapping(path = "/user/{lastName}/{firstName}", method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.CREATED)
//    public long addNewUser (@PathVariable("lastName") String lastName, @PathVariable("firstName") String firstName) {
//        AppUser savedUser = userRepository.save(new AppUser(firstName, lastName));
//
//        LOG.info(savedUser.toString() + " successfully saved into DB");
//
//        return savedUser.getId();
//    }


}
