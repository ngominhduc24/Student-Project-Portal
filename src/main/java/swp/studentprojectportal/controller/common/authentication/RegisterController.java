package swp.studentprojectportal.controller.common.authentication;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.SettingService;
import swp.studentprojectportal.service.servicesimpl.UserService;
import swp.studentprojectportal.utils.Validate;

@Controller
public class RegisterController {
    @Autowired
    int userRoleId;
    @Autowired
    UserService userService;
    @Autowired
    SettingService settingService;
    @GetMapping("/register")
    public String registerPage(Model model) {
        return "register";
    }
    @PostMapping("/register")
    public String registerAccount(WebRequest request, Model model, HttpSession session) {
        String fullname = request.getParameter("fullname");
        String termCheckbox = request.getParameter("termCheckbox");
        String username = request.getParameter("username").replace(" ", "");
        String password = request.getParameter("password");

        // create model user
        User user = new User();
        user.setActive(false);
        user.setFullName(fullname);
        user.setPassword(password);
        user.setSetting(settingService.findById(userRoleId));

        if(Validate.validEmail(username)) {
            user.setEmail(username);
            session.setAttribute("verifyMail", true);
        }
        if(Validate.validPhoneNumber(username)) {
            user.setPhone(username);
            session.setAttribute("verifyMail", false);
        }

        if(termCheckbox == null) {
            model.addAttribute("errmsg", "You must agree with our term and condition");
            return "authentication/register";
        }

        if(user.getEmail() == null && user.getPhone() == null) {
            model.addAttribute("errmsg", "Invalid email or phone number");
            return "authentication/register";
        }

        if(user.getEmail() != null && userService.checkExistMail(user.getEmail())) {
            model.addAttribute("errmsg", "Email already exist!");
            return "authentication/register";
        }

        if(user.getEmail() != null &&!userService.checkEmailDomain(user.getEmail())) {
            model.addAttribute("errmsg", "Email domain is not allowed!");
            return "authentication/register";
        }

        if (user.getPhone() != null && userService.checkExistPhoneNumber(user.getPhone())) {
            model.addAttribute("errmsg", "Phone number already exist!");
            return "authentication/register";
        }

        if(Validate.validPassword(password) == false) {
            model.addAttribute("errmsg", "Password must contain at least 8 characters and have uppercase, lowercase, and number");
            return "authentication/register";
        }

        // set session to verify
        session.setAttribute("userauthen", user);
        session.setAttribute("href", "verify");
        return "redirect:/verifypage";
    }
}
