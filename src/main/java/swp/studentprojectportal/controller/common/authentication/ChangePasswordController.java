package swp.studentprojectportal.controller.common.authentication;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.UserService;
import swp.studentprojectportal.utils.Utility;
import swp.studentprojectportal.utils.Validate;

import java.security.NoSuchAlgorithmException;

@Controller
public class ChangePasswordController {
    @Autowired
    UserService userService;
    @GetMapping("/change-password")
    public String changePasswordForm() {
        return "changePassword";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword,
                                 @RequestParam String reNewPassword, Model model, HttpSession session) throws NoSuchAlgorithmException {
        User user= (User) session.getAttribute("user");
        if(!user.getPassword().equals(Utility.hash(oldPassword))){
            model.addAttribute("errmsg", "Old Password is incorrect");
        } else {
            if(!newPassword.equals(reNewPassword)){
                model.addAttribute("errmsg", "New Password and Re-new Password do not match");
            } else {
                if(Validate.validPassword(newPassword) == false) {
                    model.addAttribute("errmsg", "Password must contain at least 8 characters and have uppercase, lowercase, and number");
                } else {
                    if (oldPassword.equals(newPassword)){
                        model.addAttribute("errmsg", "New password must be different from the old password");
                    } else {
                        session.setAttribute("user", user);
                        model.addAttribute("msg", "Change password successfully");
                        try {user.setPassword(newPassword);}
                        catch (NoSuchAlgorithmException e) {throw new RuntimeException(e);}
                        userService.saveUser(user);
                        session.setAttribute("user", user);
                    }
                }
            }
        }
        return "authentication/changePassword";
    }



}
