package swp.studentprojectportal.controller.subject_manager;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.ISubjectService;

import java.util.List;

@Controller
public class ManagerHomeController {
    @Autowired
    ISubjectService subjectService;
    @GetMapping("/subject-manager/home")
    public String managerHome(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("subjectList", subjectService.findAllSubjectByUserAndStatus(user, true));
        return "/subject_manager/managerHome";
    }
}
