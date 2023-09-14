package swp.studentprojectportal.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class registerController {
    @RequestMapping("/register")
    public String registerPage() {
        return "register";
    }
}
