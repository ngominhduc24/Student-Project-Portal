package swp.studentprojectportal.controller.common;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.services.servicesimpl.RegisterService;
import swp.studentprojectportal.services.servicesimpl.UserService;
import swp.studentprojectportal.utility.Validate;
import swp.studentprojectportal.utils.Utility;

@Controller
public class registerController {
    @Autowired
    UserService userService;
    @GetMapping("/register")
    public String registerPage(Model model) {
        return "register";
    }
    @PostMapping("/register")
    public String registerAccount(WebRequest request, Model model, HttpSession session) {
        String fullname = request.getParameter("fullname");
        String username = request.getParameter("username").replace(" ", "");
        String password = request.getParameter("password").replace(" ", "");

        // create model user
        User user = new User();
        user.setActive(false);
        user.setFullName(fullname);
        if(Validate.validEmail(username)) {user.setEmail(username);}
        if(Validate.validPhoneNumber(username)) {user.setPhone(username);}
        user.setPassword(password);

        if(user.getEmail() == null && user.getPhone() == null) {
            return "redirect:/register";
        }

        if(user.getEmail() != null && userService.checkExistMail(user.getEmail()) && !userService.checkEmailDomain(user.getEmail())) {
            return "redirect:/register";
        }

        // set session to verify
        session.setAttribute("userauthen", user);
        session.setAttribute("href", "verify");
        return "redirect:/verifypage";
    }
}
