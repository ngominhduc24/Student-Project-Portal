package swp.studentprojectportal.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.services.servicesimpl.UserServices;
import swp.studentprojectportal.utility.Validate;

@Controller
public class registerController {
    @Autowired
    UserServices userServices;
    @GetMapping("/register")
    public String registerPage(Model model) {
        return "register";
    }
    @PostMapping("/register")
    public String registerAccount(WebRequest request, Model model, HttpSession session) {
        String fullname = request.getParameter("fullname");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // create model user
        User user = new User();
        user.setFullName(fullname);
        if(Validate.validEmail(username)) {user.setEmail(username);}
        if(Validate.validPhoneNumber(username)) {user.setPhone(username);}
        user.setPassword(password);

        // set session to verify
        session.setAttribute("user", user);
        return "register";
    }
}
