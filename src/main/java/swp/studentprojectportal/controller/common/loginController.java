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
import swp.studentprojectportal.services.servicesimpl.SettingService;
import swp.studentprojectportal.services.servicesimpl.UserService;
import swp.studentprojectportal.utils.GooglePojo;
import swp.studentprojectportal.utils.GoogleUtils;

import java.io.IOException;

@Controller
public class loginController {
    private final String afterLoginRoute = "/home";

    @Autowired
    UserService userService;
    @Autowired
    SettingService settingService;
    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String userLogin(@RequestParam String username, @RequestParam String password,
                            Model model, HttpSession session) {
        User user;
        if (username.contains("@"))
            user = userService.findUserByEmailAndPassword(username, password);
        else
            user = userService.findUserByPhoneAndPassword(username, password);
        if(user != null) {
            if(!user.isActive()) {
                model.addAttribute("errmsg", "Your account has not been verified");
                return "login";
            }
            if(!user.isStatus()) {
                model.addAttribute("errmsg", "Your account has been blocked");
                return "login";
            }
            session.setAttribute("user", user);
            return "redirect:" + afterLoginRoute;
        }
        else {
            model.addAttribute("errmsg", "Username or password is not correct");
            return "login";
        }
    }

    @GetMapping("/login-google")
    public String userLoginGoogle(@RequestParam String code, Model model, HttpSession session) throws IOException {
        if (code == null || code.isEmpty()) {
            return "redirect:/login";
        } else {
            String accessToken = GoogleUtils.getToken(code);
            GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);
            if (!userService.checkEmailDomain(googlePojo.getEmail())) {
                model.addAttribute("errmsg", "Your account is not allowed to log into the system");
                return "login";
            }
            if (!userService.checkExistMail(googlePojo.getEmail())) {
                User user = userService.registerAccountFromGoogle(googlePojo);
                session.setAttribute("user", user);
                return "redirect:" + afterLoginRoute;
            } else {
                User user = userService.findUserByEmailAndPassword(googlePojo.getEmail(), googlePojo.getId());
                if (!user.isStatus()) {
                    model.addAttribute("errmsg", "Your account has been blocked");
                    return "login";
                }
                session.setAttribute("user", user);
                return "redirect:" + afterLoginRoute;
            }

        }
    }

    @GetMapping("/logout")
    public String logout(WebRequest request, HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }
}
