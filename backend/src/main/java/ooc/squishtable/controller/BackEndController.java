package ooc.squishtable.controller;

import lombok.var;
import ooc.squishtable.model.AppTask;
import ooc.squishtable.model.AppUser;
import ooc.squishtable.service.IAdminService;
import ooc.squishtable.service.IUserService;
import ooc.squishtable.service.UserDetailsServiceImpl;
import ooc.squishtable.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/")
public class BackEndController {

    /*
     Just good services
     */
    @Autowired
    IUserService userService;

    @Autowired
    IAdminService adminService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    private Boolean success = true;
    private ErrorType error = ErrorType.NO_ERROR;

    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";
    public static final String SECURED_TEXT = "Hello from the secured resource!";


    @RequestMapping(path = "/hello")
    public String sayHello() {
        return HELLO_TEXT;
    }

    @RequestMapping(path = "/user/{lastName}/{firstName}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public long addNewUser (@PathVariable("lastName") String lastName, @PathVariable("firstName") String firstName) {

        return 5;
    }

    @GetMapping(path = "/user/{id}")
    public AppUser getUserById(@PathVariable("id") long id) {

        return new AppUser();
    }

    @RequestMapping(path="/secured", method = RequestMethod.GET)
    public @ResponseBody String getSecured() {
        return SECURED_TEXT;
    }

    // Forwards all routes to FrontEnd except: '/', '/index.html', '/api', '/api/**'
    // Required because of 'mode: history' usage in frontend routing, see README for further details
    @RequestMapping(value = "{_:^(?!index\\.html|api).*$}")
    public String redirectApi() {
        return "forward:/";
    }
}