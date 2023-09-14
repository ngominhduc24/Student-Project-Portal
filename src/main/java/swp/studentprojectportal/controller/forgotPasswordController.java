package swp.studentprojectportal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class forgotPasswordController {
    @RequestMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }
}
