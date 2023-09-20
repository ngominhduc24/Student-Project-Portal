package swp.studentprojectportal.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.services.servicesimpl.SettingService;
import swp.studentprojectportal.services.servicesimpl.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class userController {
    @Autowired
    UserService userService;

    @Autowired
    SettingService settingService;

    @GetMapping("/user")
    public String userList(Model model) {
        model.addAttribute("userList", userService.findAllUser());
        return "admin/user/userList";
    }

    @GetMapping("/addUser")
    public String addUserGet(Model model) {
        model.addAttribute("roleList", settingService.getAllRole());
        return "admin/user/userAdd";
    }

    @PostMapping ("/addUser")
    public String addUser(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam int roleId,
            @RequestParam String password,
            Model model) {

        String errorMsg = null;
        if(userService.checkExistMail(email)) errorMsg = "Email existed!";
        if(userService.checkExistPhoneNumber(phone)) errorMsg = "Phone existed!";

        if(errorMsg!=null) {
            model.addAttribute("error", errorMsg);
            model.addAttribute("roleList", settingService.getAllRole());
            return "admin/user/userAdd";
        }

        userService.addUser(fullName, email, phone, password, roleId);
        return "redirect:./user";
    }

    @PostMapping("/updateUser")
    public String updateUser(
            @RequestParam int id,
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam int roleId,
            @RequestParam String note,
            @RequestParam boolean status) {
        userService.updateUser(id, fullName, email, phone, roleId, status, note);
        return "redirect:./user";
    }

    @GetMapping("/userDetails")
    public String userDetails(Model model, @RequestParam int id) {
        Optional<User> user = userService.findUserById(id);
        model.addAttribute("user", user.isPresent() ? user.get() : null);
        model.addAttribute("roleList", settingService.getAllRole());
        return "admin/user/userDetails";
    }

    @GetMapping("/updateUserStatus")
    public String updateSettingStatus(
            @RequestParam int id,
            @RequestParam boolean status) {
        userService.updateUserStatus(id, status);
        return "redirect:/";
    }
}
