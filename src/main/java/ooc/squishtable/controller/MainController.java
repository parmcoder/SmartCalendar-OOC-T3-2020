package ooc.squishtable.controller;

import lombok.var;
import ooc.squishtable.model.AppUser;
import ooc.squishtable.service.IAdminService;
import ooc.squishtable.service.UserDetailsServiceImpl;
import ooc.squishtable.utils.WebUtils;
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
    @Autowired
    IAdminService adminService;
//
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    private Boolean success = true;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This website is made by Parmcoder!");
        return "welcomePage";
    }

    /*
    * TODO: We need to make log-in page
     */
    @RequestMapping(value = { "/", "/login"}, method = RequestMethod.GET)
    public String loginPage(Model model, Principal principal){
        model.addAttribute("title", "Welcome");

        if (principal != null) {
            User loggedinUser = (User) ((Authentication) principal).getPrincipal();
            if(loggedinUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) return "redirect:admin";
            else return "redirect:user";
        }
        System.out.println("Hello");
        return "loginPage";
    }

    /*
     * TODO: We need to make calendar page
     */

    /*
     * TODO: We need to make registration page
     */

    /*
     * TODO: We need to make admin page
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {

        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        //only admin can log in here

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);
        var users = adminService.findAll();
        model.addAttribute("appUserList", users);
        model.addAttribute("userRow", new AppUser());

        return "adminpage";
    }

    /*
     * TODO: We need to make user page for adding task
     */
    @RequestMapping(value = "/user")
    public String showUserInfo(Model model, Principal principal){
        // After user login successfully.
        String userName = principal.getName();

        System.out.println("AppUser Name: " + userName);

        User loggedinUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loggedinUser);
        model.addAttribute("userInfo", userInfo);

        AppUser editUser = new AppUser();
        model.addAttribute("newUserData", editUser);

        AppUser currentUser = adminService.getCurrentInfo(userName);
        model.addAttribute("currentUser", currentUser);

        return "userInfoPage";
    }

    /*
     * TODO: We need to handle 403 page
     */
    @RequestMapping(value = {"/403"}, method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {

            User loggedinUser = (User) ((Authentication) principal).getPrincipal();
            String userInfo = WebUtils.toString(loggedinUser);
            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "welcomePage";
    }


    /*
     * TODO: We need to manage menu bar
     */

    /*
     ? We need to make about page (Optional)
     */

}