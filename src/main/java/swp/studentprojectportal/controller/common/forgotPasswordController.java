package swp.studentprojectportal.controller.common;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.services.servicesimpl.RegisterService;
import swp.studentprojectportal.services.servicesimpl.ResetPassword;
import swp.studentprojectportal.services.servicesimpl.UserService;
import swp.studentprojectportal.utility.Validate;

@Controller
public class forgotPasswordController {
    @Autowired
    UserService userService;
    @Autowired
    RegisterService registerService;
    @Autowired
    ResetPassword resetPassword;
    @GetMapping("/forgotPassword")
    public String forgotPasswordPage(HttpSession session) {
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(Model model, WebRequest request, HttpSession session) {
        String username = request.getParameter("username");

        User user = resetPassword.getUserByEmailOrPhone(username);
        if(user == null) {
            model.addAttribute("errmsg", "Username is't not correct");
            return "forgotPassword";
        }

        if(Validate.validEmail(username)) {
            user.setEmail(username);
            session.setAttribute("verifyMail", true);
        }
        if(Validate.validPhoneNumber(username)) {
            user.setPhone(username);
            session.setAttribute("verifyMail", false);
        }

        if(user.getEmail() != null && !userService.checkExistMail(user.getEmail())) {
            model.addAttribute("errmsg", "Your email is not correct");
            return "forgotPassword";
        }

        if(user.getEmail() != null && !userService.checkEmailDomain(user.getEmail())) {
            model.addAttribute("errmsg", "Your email domain is not accepted");
            return "forgotPassword";
        }

        if(user.getPhone() != null && !userService.checkExistPhoneNumber(user.getPhone())) {
            model.addAttribute("errmsg", "Your phone number is not correct");
            return "forgotPassword";
        }

        session.setAttribute("userauthen", user);
        session.setAttribute("href", "reset-password");
        return "redirect:/verifypage";
    }

    @GetMapping("/reset-password")
    public String resetPasswordForm(HttpSession session,@RequestParam("key") String token) {
        User user = resetPassword.resetPasswordByToken(token);
        if(user != null) {
            session.removeAttribute("user");
            session.setAttribute("user", user);
            return "resetPassword";
        } else {
            return "redirect:/forgotPassword";
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(WebRequest request, Model model, HttpSession session) {
        String newPassword = request.getParameter("newPassword");
        String reNewPassword = request.getParameter("reNewPassword");
        User user= (User) session.getAttribute("user");

        // check old password empty
        if(user.getPassword() != "") {
            return "redirect:/forgotPassword";
        }

        // check equal password and re-password
        if(newPassword.equals(reNewPassword)){
            user.setPassword(newPassword);
            session.setAttribute("user", user);
            model.addAttribute("errmsg", "Reset password successfully");
            //save to database
            User u = userService.saveUser(user);
            return "redirect:/login";
        } else {
            model.addAttribute("errmsg", "New Password and Re-new Password do not match");
        }

        return "resetPassword";
    }

}
