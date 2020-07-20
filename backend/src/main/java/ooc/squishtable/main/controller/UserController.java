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
@RequestMapping("/api/user/")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";
    public static final String SECURED_TEXT = "Hello from the secured resource!";

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/user/hello")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public String sayHello() {
        LOG.info("GET called on /hello resource");
        return HELLO_TEXT;
    }

    @GetMapping(path = "{username}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public AppUser getUserByUsername(@PathVariable("username") String username) {
        return adminService.getUser(username);
    }

    @PostMapping(path = "/user/create/{username}/{title}/{description}/{dateStart}/{dateEnd}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity addTask(@PathVariable("title") String title, @PathVariable("description") String description,
                                          @PathVariable("dateStart") String dateStart, @PathVariable("dateEnd") String dateEnd,
                                  @PathVariable("username") String username) {
        AppTask creating = new AppTask(title, description, dateStart, dateEnd);
        if (userService.addTask(creating,username))
            return ResponseEntity.status(HttpStatus.CREATED).body(creating);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    /*
    TODO: Get a good task list to display from username
    */
    @PostMapping(path = "/user/tasklist/{username}")
    public ResponseEntity displayAllTasks(@PathVariable("username") String username){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllTasks(username));
    }

    /*
    TODO: Update task on task id
    */
    @PostMapping(path = "/user/edit/{title}/{description}/{dateStart}/{dateEnd}")
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
    @PostMapping(path = "/user/remove/{title}/{description}/{dateStart}/{dateEnd}")
    public ResponseEntity removeTask(@PathVariable("title") String title, @PathVariable("description") String description,
                                     @PathVariable("dateStart") String dateStart, @PathVariable("dateEnd") String dateEnd){
        AppTask task = new AppTask(title, description, dateStart, dateEnd);
        userService.removeTask(task);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /*
    TODO: Edit user info
    */
    @PostMapping(path = "/user/edit/{username}/{password}/{name}/{surname}")
    public ResponseEntity editUser(@PathVariable("username") String username, @PathVariable("password") String password,
                                   @PathVariable("name") String name, @PathVariable("surname") String surname){
        AppUser currentUser = adminService.getUser(username);
        currentUser.setName(name);
        currentUser.setSurname(surname);
        currentUser.setUsername(username);
        currentUser.setPassword(password);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


}
