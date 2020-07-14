package ooc.squishtable.controller;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class MainController  {

    /*
     Just good services
     */
//
//    @Autowired
//    IAdminService adminService;
//
//    @Autowired
//    UserDetailsServiceImpl userDetailsService;

    private Boolean success=true;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This website is made by Parmcoder!");
        return "welcomePage";
    }

    /*
    * TODO: We need to make log-in page
     */

    /*
     * TODO: We need to make calendar page
     */

    /*
     * TODO: We need to make registration page
     */

    /*
     * TODO: We need to make admin page
     */

    /*
     * TODO: We need to make user page for adding task
     */

    /*
     * TODO: We need to handle 403 page
     */

    /*
     * TODO: We need to manage menu bar
     */

    /*
     ? We need to make about page (Optional)
     */

}