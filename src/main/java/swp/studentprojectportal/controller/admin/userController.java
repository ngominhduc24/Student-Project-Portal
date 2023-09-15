package swp.studentprojectportal.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.services.servicesimpl.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class userController {
    @Autowired
    UserService userService;

    @GetMapping("/user")
    public String userList(Model model) {
        model.addAttribute("userList", userService.getAllUser());
        return "admin/userList";
    }

    @GetMapping("/addUser")
    public String addUser() {
        //register
        return "admin/addUser";
    }

    @GetMapping("/userDetails")
    public String userDetails(Model model, @RequestParam int id) {
        Optional<User> user = userService.getUserById(id);
        model.addAttribute("user", user.isPresent() ? user.get() : null);
        return "admin/userDetails";
    }
}
