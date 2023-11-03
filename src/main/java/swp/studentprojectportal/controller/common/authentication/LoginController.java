package swp.studentprojectportal.controller.common.authentication;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.SettingService;
import swp.studentprojectportal.service.servicesimpl.UserService;
import swp.studentprojectportal.utils.GooglePojo;
import swp.studentprojectportal.utils.GoogleUtils;

import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    SettingService settingService;
    @RequestMapping("/login")
    public String loginPage(
            @CookieValue(value = "cuser", defaultValue = "") String cuser,
            @CookieValue(value = "cpass", defaultValue = "") String cpass,
            @CookieValue(value = "crem", defaultValue = "") String crem,
            Model model) {
        model.addAttribute("cuser", cuser);
        model.addAttribute("cpass", cpass);
        model.addAttribute("crem", crem);
        return "login";
    }

    @PostMapping("/login")
    public String userLogin(@RequestParam String username, @RequestParam String password,
            Model model, HttpSession session, HttpServletResponse response, WebRequest request) {
        username = username.replace("+84", "0").replace(" ", "");
        model.addAttribute("cuser", username);
        model.addAttribute("cpass", password);
        if(username.length()>35){
            model.addAttribute("errmsg", "Your username is too long");
            return "authentication/login";
        }
        User user = userService.findUserByUsernameAndPassword(username.trim(), password);
        if(user != null && user.isActive() && user.isStatus()) {
            session.setAttribute("user", user);
            //add to cookie
            String remember = request.getParameter("remember");
            Cookie cu= new Cookie("cuser", username);
            Cookie cp= new Cookie("cpass", password);
            Cookie cr= new Cookie("crem", remember);
            userService.setCookie(cu,cp,cr,remember);
            response.addCookie(cu);
            response.addCookie(cp);
            response.addCookie(cr);
            if(user.getSetting().getId()==1)
                return "redirect:student/home" ;
            if(user.getSetting().getId()==2)
                return "redirect:admin/home" ;
            if(user.getSetting().getId()==3)
                return "redirect:subject-manager/home" ;
            if(user.getSetting().getId()==4)
                return "redirect:class-manager/home" ;
        } else if (user==null){
            model.addAttribute("errmsg", "Username or password is not correct");
        } else if(!user.isActive()) {
            model.addAttribute("errmsg", "Your account has not been verified");
            session.setAttribute("userauthen", user);
            session.setAttribute("href", "verify");
            return "redirect:/verifypage";
        } else if(!user.isStatus()) {
            model.addAttribute("errmsg", "Your account has been blocked");
        }
        return "authentication/login";
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
            User user = new User();
            if (!userService.checkExistMail(googlePojo.getEmail())) {
                user = userService.registerAccountFromGoogle(googlePojo);
                session.setAttribute("user", user);
            } else {
                user = userService.findUserByEmailAndPassword(googlePojo.getEmail(), googlePojo.getId());
                if (!user.isStatus()) {
                    model.addAttribute("errmsg", "Your account has been blocked");
                    return "login";
                }
                session.setAttribute("user", user);
            }
            if(user.getSetting().getId()==2)
                return "redirect:admin/home" ;
            else if(user.getSetting().getId()==3)
                return "redirect:subject-manager/home" ;
            else if (user.getSetting().getId()==4)
                return "redirect:class-manager/home" ;
            else
                return "redirect:admin/home" ;
        }
    }

    @GetMapping("/logout")
    public String logout(WebRequest request, HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }
}
