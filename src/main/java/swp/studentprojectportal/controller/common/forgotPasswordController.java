package swp.studentprojectportal.controller.common;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.services.servicesimpl.UserService;

@Controller
public class forgotPasswordController {
    @Autowired
    UserService userService;
    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @GetMapping("/reset-password")
    public String resetPasswordForm() {
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(WebRequest request, Model model, HttpSession session) {
        String newPassword = request.getParameter("newPassword");
        String reNewPassword = request.getParameter("reNewPassword");
        User user= (User) session.getAttribute("user");
        if(newPassword.equals(reNewPassword)){
            user.setPassword(newPassword);
            session.setAttribute("user", user);
            model.addAttribute("errmsg", "Reset password successfully");
            //save to database
            User u = userService.registerNewAccount(user);
        } else {
            model.addAttribute("errmsg", "New Password and Re-new Password do not match");
        }

        return "reset-password";
    }

}
