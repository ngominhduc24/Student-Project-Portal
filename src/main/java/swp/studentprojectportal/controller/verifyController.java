package swp.studentprojectportal.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.services.servicesimpl.UserService;

@Controller
public class verifyController {
    @Autowired
    UserService userService;
    @GetMapping
    public String verifyPage(Model model, HttpSession session) {
        User user =  (User)session.getAttribute("user");
        if(user.getEmail() != null) {
            if(userService.checkExistMail(user.getEmail()) && userService.checkEmailDomain(user.getEmail())) {
            return "verifyEmail";
            }
        }
        if(user.getPhone() != null) {
            if(userService.checkExistPhoneNumber(user.getPhone())) {
                return "verifyPhone";
            }
        }
        return "redirect:/error";
    }

    @PostMapping
    public String registerAccount(Model model, HttpSession session) {
        return "dashboard";
    }
}
