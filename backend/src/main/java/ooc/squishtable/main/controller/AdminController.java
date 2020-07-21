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

    /*
        TODO: Parm needs to test these methods and what the responses are sent back to frontend
    */
    @GetMapping(path = "/userlist")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity displayAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.findAll());
    }

    /*
    TODO: Remove user using username or id (ADMIN & optional)
    */
    @PostMapping(path = "/remove/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity removeUser(@PathVariable("username") String username){
        AppUser userToRemove = adminService.getUser(username);
        if(adminService.removeUser(userToRemove)) return ResponseEntity.status(HttpStatus.FOUND).body("User has been removed.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User has been found.");
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
