package swp.studentprojectportal.controller.subject_manager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerHomeController {
    @GetMapping("/subject-manager/home")
    public String managerHome(Model model) {
        return "/subject_manager/managerHome";
    }
}
