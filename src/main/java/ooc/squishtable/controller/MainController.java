package ooc.squishtable.controller;

import lombok.var;
import ooc.squishtable.model.AppTask;
import ooc.squishtable.model.AppUser;
import ooc.squishtable.service.IAdminService;
import ooc.squishtable.service.IUserService;
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
public class MainController {

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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showFirstPage(Principal principal) {
        if (principal != null) {
            return "redirect:login";
        }
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Principal principal) {
        if (principal != null) {
            User loggedinUser = (User) ((Authentication) principal).getPrincipal();
            if (loggedinUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
                return "redirect:admin";
            else return "redirect:user";
        }
        return "newLoginPage";
    }

    @RequestMapping(value = "/registration")
    public String showRegistration(Model model, Principal principal) {
        // After user login successfully.
        if (principal != null) {
            User loggedinUser = (User) ((Authentication) principal).getPrincipal();
            if (loggedinUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
                return "redirect:admin";
            else return "redirect:user";
        }
        model.addAttribute("userToAdd", new AppUser());
        return "registration";
    }

    @RequestMapping(value = "/registration/failed")
    public String showRegistrationFailed(Model model, Principal principal) {
        if (error.equals(ErrorType.PASSWORD_MISMATCH))
            model.addAttribute("errorMsg", "Registration Failed! Passwords do not match.");
        if (error.equals(ErrorType.USERNAME_TAKEN))
            model.addAttribute("errorMsg", "Registration Failed! Username is already taken.");
        error = ErrorType.NO_ERROR;
        return showRegistration(model, principal);
    }

    @PostMapping(value = "/registration")
    public String submitRegistration(@ModelAttribute AppUser user,
                                     Model model) {
        if (adminService.checkMatching(user)) {
            success = adminService.addNewUser(user);
            if (success) {
                return "redirect:";
            }
            error = ErrorType.USERNAME_TAKEN;
        }
        error = ErrorType.PASSWORD_MISMATCH;
        return "forward:registration/failed";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {
        model.addAttribute("title", "Admin Page");
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        //only admin can log in here

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);
        var users = adminService.findAll();
        model.addAttribute("appUserList", users);

        return "adminpage";
    }

    @RequestMapping(value = "/user")
    public String showUserInfo(Model model, Principal principal) {
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

    @RequestMapping(value = "/user/failed", method = RequestMethod.GET)
    public String userInfoFailed(Model model, Principal principal) {
        // After user login successfully.
        showUserInfo(model, principal);
        if (!success) {
            model.addAttribute("errorMsg", "Try again! Your username is not available.");
        }
        success = true;
        return "userInfoPage";
    }

    @PostMapping(value = "/user")
    public String updateUserInfo(@ModelAttribute("newUserData") AppUser user, Principal principal, Model model) {
        success = adminService.updateUserInfo(principal.getName(), user);
        if (user.getUsername().isEmpty()) {
            return "redirect:user";
        }
        if (!success) {
            return "redirect:user/failed";
        }
        return "redirect:logout";
    }

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

        return "403Page";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public String remove(@ModelAttribute(value = "userRow") AppUser user, Model model) {
        return "removeConfirm";
    }

    @PostMapping(value = "/remove")
    public String confirmRemoveClicked(@ModelAttribute(value = "userRow") AppUser user, Model model) {
        success = adminService.removeUser(user);

        if (!success) {
            model.addAttribute("errorMsg", "Try again! That user is not existed.");
        }

        return "redirect:admin";
    }

    /*
     * TODO: We need to make calendar page
     */
    @RequestMapping(value = {"/calendar"}, method = RequestMethod.GET)
    public String showCalendar(Model model, Principal principal) {

        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        var allTask = userService.getAllTasks(loginedUser.getUsername());
        model.addAttribute("userTasksList", allTask);

        return "calendarpage";
    }

    /*
     ? We could use a modal component to show dialog box (Frontend stuff)
     */
    @RequestMapping(value = {"/calendar/add"}, method = RequestMethod.GET)
    public String showAddTask(Model model) {
        AppTask newTask = new AppTask();
        model.addAttribute("newTaskData", newTask);
        return "addTask";
    }

    @RequestMapping(value = {"/calendar/add/failed"}, method = RequestMethod.GET)
    public String failedAddTask(Model model) {
        model.addAttribute("errorMsg", "Try again! The timestamps are not valid.");
        return showAddTask(model);
    }

    @PostMapping(value = "/calendar/add")
    public String addingTask(@ModelAttribute("newTaskData") AppTask newTask, Principal principal){
        User loggedInUser = (User) ((Authentication) principal).getPrincipal();

        success = userService.addTask(newTask, loggedInUser.getUsername());
        if(success){
            return "redirect:";
        }else{
            return "forward:add/failed";
        }
    }

    /*
     TODO: I will make a task manager(table) page to handle task table for simple system
     */

    @RequestMapping(value = {"/calendar/manage"}, method = RequestMethod.GET)
    public String manageTasks(Model model, Principal principal) {

        return "tasksTable";
    }

    /*
     TODO: Confirmation system needed for remove
     */
    @RequestMapping(value = "/calendar/manage/remove", method = RequestMethod.GET)
    public String showRemoveTask(Model model, Principal principal) {

        return "tasksTable";
    }
    @PostMapping(value = "/calendar/manage/remove")
    public String confirmRemoveTask(Model model, Principal principal) {

        return "tasksTable";
    }

    /*
     TODO: Just edit
     */
    @RequestMapping(value = "/calendar/manage/edit", method = RequestMethod.GET)
    public String showEditTask(Model model, Principal principal) {

        return "tasksTable";
    }
    @PostMapping(value = "/calendar/manage/edit")
    public String editTask(Model model, Principal principal) {

        return "tasksTable";
    }

    /*
     ? We need to make about page (Optional)
     */
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String showAbout(Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This website is made by Parmcoder!");
        return "welcomePage";
    }
}