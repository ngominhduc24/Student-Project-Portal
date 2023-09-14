package swp.studentprojectportal.controller.common;

import jakarta.servlet.http.HttpSession;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.services.servicesimpl.EmailService;
import swp.studentprojectportal.services.servicesimpl.UserService;
import swp.studentprojectportal.utils.Utility;

@Controller
public class verifyController {
    @Autowired
    UserService userService;

    @Autowired
    EmailService emailservice;
    @GetMapping("/verify")
    public String verifyPage(Model model, HttpSession session,@Param("href") String href) {
        User user =  (User)session.getAttribute("user");

        // if user register by email address
        if(user.getEmail() != null) {
            String token = RandomString.make(30);
            user.setToken(token);
            userService.saveUser(user);
            // email sending
            String token_sender = Utility.getSiteURL() + "/" + href + "?token=" + token;
            emailservice.sendEmail(user.getFullName(), user.getEmail(), token_sender);

            model.addAttribute("email", user.getEmail());
            return "verifyEmail";
        }

        // if user register by phone number
        if(user.getPhone() != null) {
            if(userService.checkExistPhoneNumber(user.getPhone())) {
                return "verifyPhone";
            }
        }
        return "redirect:/error";
    }

    @PostMapping("/verify")
    public String registerAccount(Model model, HttpSession session) {
        return "dashboard";
    }
}
