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
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController()
@RequestMapping("/api/admin")
public class AdminController {

    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    public static final String SECURED_TEXT = "Hello Admin!";

    @Autowired
    private AdminService adminService;

    @GetMapping(path = "/userlist")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity displayAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.findAll());
    }

    @PostMapping(path = "/remove/{username}")
    public ResponseEntity removeUser(@PathVariable("username") String username){
        AppUser userToRemove = adminService.getUser(username);
        if(adminService.removeUser(userToRemove)) return ResponseEntity.status(HttpStatus.FOUND).body("User has been removed.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AppText("User has been found."));
    }


    @GetMapping(path = "/secured")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody ResponseEntity getSecured() {
        LOG.info("GET successfully called, so you can go to /admin.");
        return ResponseEntity.ok(new AppText(SECURED_TEXT));
    }

    /*
    ? For testing
     */

    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody String getAdminHello() {
        LOG.info("Admin?");
        return "Admin: " + SECURED_TEXT;
    }

}
