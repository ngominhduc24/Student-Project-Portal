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
public class changePasswordController {
    @Autowired
    UserService userService;
    @GetMapping("/change-password")
    public String changePasswordForm() {
        return "changePassword";
    }

    @PostMapping("/change-password")
    public String changePassword(WebRequest request, Model model, HttpSession session){
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String reNewPassword = request.getParameter("reNewPassword");
        User user= (User) session.getAttribute("user");
        if(user.getPassword().equals(oldPassword)){
            if(newPassword.equals(reNewPassword)){
                user.setPassword(newPassword);
                session.setAttribute("user", user);
                model.addAttribute("errmsg", "Change password successfully");
                //save to database
                User u = userService.saveUser(user);
            } else {
                model.addAttribute("errmsg", "New Password and Re-new Password do not match");
            }
        } else {
            model.addAttribute("errmsg", "Old Password is incorrect");
        }
        return "changePassword";
    }



}
