package swp.studentprojectportal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class landingPageController {
     @RequestMapping("/")
     public String landingPage() {
         return "landingPage";
    }
}
