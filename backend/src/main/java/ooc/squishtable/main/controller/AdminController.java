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
@RequestMapping("/api/admin")
public class AdminController {

    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";
    public static final String SECURED_TEXT = "Hello from the secured resource!";

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;
    /*
    TODO: Get a good user list to display (ADMIN & optional)
    */
    @PostMapping(path = "/userlist")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity displayAllUsers(){
        System.out.println(adminService.findAll());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    /*
    TODO: Remove user using username or id (ADMIN & optional)
    */
    @PostMapping(path = "/remove/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity removeUser(@PathVariable("username") String username){
        AppUser userToRemove = adminService.getUser(username);
        adminService.removeUser(userToRemove);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }



    /*
    ? The security part needed to be configured by frontend (vuex)
     */
    @GetMapping(path = "/secured")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody String getSecured() {
        LOG.info("GET successfully called on /secured resource");
        return SECURED_TEXT;
    }

    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody String getAdminHello() {
        LOG.info("Admin?");
        return "Admin: " + SECURED_TEXT;
    }


}
