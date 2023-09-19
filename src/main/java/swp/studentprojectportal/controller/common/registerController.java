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
        String termCheckbox = request.getParameter("termCheckbox");
        String username = request.getParameter("username").replace(" ", "");
        String password = request.getParameter("password").replace(" ", "");

        if(termCheckbox == null) {
            model.addAttribute("errmsg", "you need accept our Terms And Condition to register account");
        }

        // create model user
        User user = new User();
        user.setActive(false);
        user.setFullName(fullname);
        if(Validate.validEmail(username)) {user.setEmail(username);}
        if(Validate.validPhoneNumber(username)) {user.setPhone(username);}
        user.setPassword(password);

        if(user.getEmail() == null && user.getPhone() == null) {
            model.addAttribute("errmsg", "Your email address or phone number is not correct format");
            return "register";
        }

        if(user.getEmail() != null && userService.checkExistMail(user.getEmail())) {
            model.addAttribute("errmsg", "Email address already exist!");
            return "register";
        }

        if(user.getEmail() != null &&!userService.checkEmailDomain(user.getEmail())) {
            model.addAttribute("errmsg", "Email domain is not accept");
            return "register";
        }

        // set session to verify
        session.setAttribute("userauthen", user);
        session.setAttribute("href", "verify");
        return "redirect:/verifypage";
    }
}
