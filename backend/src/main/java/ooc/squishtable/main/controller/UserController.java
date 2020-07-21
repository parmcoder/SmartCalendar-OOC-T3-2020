package ooc.squishtable.main.controller;

import ooc.squishtable.main.model.AppTask;
import ooc.squishtable.main.model.AppText;
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

    /**
     * Print message
     * @return hello message
     */
    @RequestMapping(path = "/hello")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public String sayHello() {
        LOG.info("GET called on /hello resource");
        return HELLO_TEXT;
    }

    /**
     * Get user by using username
     * @param username
     * @return user
     */
    @GetMapping(path = "{username}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity getUserByUsername(@PathVariable("username") String username) {
        AppUser user = adminService.getUser(username);
        if (user != null)
            return ResponseEntity.status(HttpStatus.FOUND).body(user);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AppText("User does not exist, try again."));
    }

    /**
     * Add task
     * @param title
     * @param description
     * @param dateStart
     * @param dateEnd
     * @param username
     * @return Task
     */

    @PostMapping(path = "create/{username}/{title}/{description}/{dateStart}/{dateEnd}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity addTask(@PathVariable("title") String title, @PathVariable("description") String description,
                                  @PathVariable("dateStart") String dateStart, @PathVariable("dateEnd") String dateEnd,
                                  @PathVariable("username") String username) {
        AppTask creating = new AppTask(title, description, dateStart, dateEnd);
        if (userService.addTask(creating, username))
            return ResponseEntity.status(HttpStatus.CREATED).body(creating);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AppText("Bad request, try again."));
    }

    /**
     * Display all user's tasks
     * @param username
     * @return user's tasks
     */

    @GetMapping(path = "tasklist/{username}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity displayAllTasks(@PathVariable("username") String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllTasks(username));
    }

    /**
     * Update user's task
     * @param tid
     * @param title
     * @param description
     * @param dateStart
     * @param dateEnd
     * @return Update task
     */
    @PostMapping(path = "edit/{tid}/{title}/{description}/{dateStart}/{dateEnd}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity updateTask(@PathVariable("tid") String tid,
                                     @PathVariable("title") String title, @PathVariable("description") String description,
                                     @PathVariable("dateStart") String dateStart, @PathVariable("dateEnd") String dateEnd
                                     ) {
        AppTask newTask = new AppTask(title, description, dateStart, dateEnd);
        if (userService.updateTask(newTask, Long.parseLong(tid)))
            return ResponseEntity.status(HttpStatus.CREATED).body("Updated task info successfully!");
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update rejected! Invalid date.");
        }
    }

    /**
     * Remove user's task
     * @param tid
     * @return Message to tell user that we successfully remove task
     */
    @PostMapping(path = "remove/{tid}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity removeTask(@PathVariable("tid") String tid) {
        AppTask task = new AppTask();
        task.setTid(Long.parseLong(tid));
        userService.removeTask(task);
        return ResponseEntity.status(HttpStatus.FOUND).body("Removed task successfully");
    }

    /**
     * Update user information
     * @param username
     * @param newusername
     * @param name
     * @param surname
     * @return Message to user
     */
    @PostMapping(path = "update/{username}/{newusername}/{name}/{surname}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity editUser(@PathVariable("username") String username,
                                   @PathVariable("newusername") String newusername,
                                   @PathVariable("name") String name, @PathVariable("surname") String surname) {
        AppUser newInfo = adminService.getUser(username);
        newInfo.setName(name);
        newInfo.setSurname(surname);
        newInfo.setUsername(newusername);
        if(adminService.updateUserInfo(username, newInfo)) return ResponseEntity.status(HttpStatus.OK).body("Updated user info successfully");
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update rejected! Invalid new username.");
        }
    }


}
