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
import org.springframework.security.access.prepost.PreAuthorize;
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
//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public String sayHello() {
        LOG.info("GET called on /hello resource");
        return HELLO_TEXT;
    }

    @GetMapping(path = "api/user/{username}")
    public AppUser getUserByUsername(@PathVariable("username") String username) {

        return adminService.getUser(username);
    }

    @PostMapping(path = "api/user/create/{username}/{password}/{name}/{surname}")
    public ResponseEntity registerNewUser(@PathVariable("username") String username, @PathVariable("password") String password,
                                          @PathVariable("name") String name, @PathVariable("surname") String surname) {
        AppUser creating = new AppUser(username, password, name, surname);
        if (adminService.addNewUser(creating)) return ResponseEntity.status(HttpStatus.CREATED).body(adminService.getUser(creating.getUsername()));
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping(path = "api/user/create/{title}/{description}/{dateStart}/{dateEnd}")
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
    @PostMapping(path = "api/user/edit/{title}/{description}/{dateStart}/{dateEnd}")
    public ResponseEntity updateTask(@PathVariable("title") String title, @PathVariable("description") String description,
                                     @PathVariable("dateStart") String dateStart, @PathVariable("dateEnd") String dateEnd,
                                     Principal principal){
        User loggedInUser = (User) ((Authentication) principal).getPrincipal();
        AppTask newTask = new AppTask(title, description, dateStart, dateEnd);
        if (userService.addTask(newTask,loggedInUser.getUsername()))
            return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /*
    TODO: Remove task on task id
    */
    @PostMapping(path = "api/user/remove/{title}/{description}/{dateStart}/{dateEnd}")
    public ResponseEntity removeTask(@PathVariable("title") String title, @PathVariable("description") String description,
                                     @PathVariable("dateStart") String dateStart, @PathVariable("dateEnd") String dateEnd){
        AppTask task = new AppTask(title, description, dateStart, dateEnd);
        userService.removeTask(task);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /*
    TODO: Edit user info
    */
    @PostMapping(path = "api/user/edit/{username}/{password}/{name}/{surname}")
    public ResponseEntity editUser(@PathVariable("username") String username, @PathVariable("password") String password,
                                   @PathVariable("name") String name, @PathVariable("surname") String surname){
        AppUser currentUser = adminService.getUser(username);
        currentUser.setName(name);
        currentUser.setSurname(surname);
        currentUser.setUsername(username);
        currentUser.setPassword(password);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /*
    TODO: Get a good user list to display (ADMIN & optional)
    */

    /*
    TODO: Remove user using username or id (ADMIN & optional)
    */
    @PostMapping(path = "api/user/remove/{username}")
    public ResponseEntity removeUser(@PathVariable("username") String username){
        AppUser userToRemove = adminService.getUser(username);
        adminService.removeUser(userToRemove);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }



    /*
    ? The security part needed to be configured by frontend (vuex)
     */
    @GetMapping(path = "/secured")
    public @ResponseBody String getSecured() {
        LOG.info("GET successfully called on /secured resource");
        return SECURED_TEXT;
    }

    @RequestMapping(path = "/admin/hello", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public @ResponseBody String getAdminHello() {
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
