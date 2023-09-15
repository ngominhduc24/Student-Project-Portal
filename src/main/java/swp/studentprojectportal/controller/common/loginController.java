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
import swp.studentprojectportal.utils.GooglePojo;
import swp.studentprojectportal.utils.GoogleUtils;

import java.io.IOException;

@Controller
public class loginController {
    @Autowired
    UserService userService;
    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String userLogin(WebRequest request, Model model, HttpSession session) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user;
        if (username.contains("@"))
            user = userService.findUserByEmailAndPassword(username, password);
        else
            user = userService.findUserByPhoneAndPassword(username, password);
        if(user != null) {
            session.setAttribute("user", user);
            return "redirect:/";
        }
        else {
            model.addAttribute("errmsg", "username or password is not correct");
            return "login";
        }
    }

    @GetMapping("/login-google")
    public String userLoginGoogle(WebRequest request, Model model, HttpSession session) throws IOException {
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            return "redirect:/login";
        } else {
            String accessToken = GoogleUtils.getToken(code);
            GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);
            User account = userService.findUserByEmailAndPassword(googlePojo.getEmail(), googlePojo.getId());
            if(account==null) {
                //User newAcc = userService.registerAccount(googlePojo);
                //session.setAttribute("account", newAcc);
                return "redirect:/home";
            } else{
                session.setAttribute("account", account);
                return "redirect:/home";
            }

        }
    }
}
