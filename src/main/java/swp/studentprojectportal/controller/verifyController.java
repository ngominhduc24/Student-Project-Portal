package swp.studentprojectportal.controller;

import jakarta.servlet.http.HttpSession;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.services.servicesimpl.Emailservice;
import swp.studentprojectportal.services.servicesimpl.RegisterService;
import swp.studentprojectportal.services.servicesimpl.UserService;
import swp.studentprojectportal.utils.Utility;

@Controller
public class verifyController {
    @Autowired
    UserService userService;

    @Autowired
    Emailservice emailservice;

    @Autowired
    RegisterService registerService;
    @GetMapping("/verify")
    public String verifyPage(Model model, HttpSession session, WebRequest webRequest) {
        User user =  (User)session.getAttribute("user");

        // if user register by email address
        if(user.getEmail() != null) {
            String token = RandomString.make(30);
            user.setToken(token);
            userService.saveUserWaitVerify(user);
            // get href
            String href = (String)session.getAttribute("href");
            session.removeAttribute("href");
            String token_sender = Utility.getSiteURL() + "/" + href + "?key=" + token;
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


    @GetMapping("/verifyaccount")
    public String registerAccount(Model model,@RequestParam("key") String token) {
        if(registerService.verifyToken(token) == true) {
            return "verifySuccess";
        }
        return "register";
    }
}
