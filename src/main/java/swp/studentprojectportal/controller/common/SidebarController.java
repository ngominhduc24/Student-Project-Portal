package swp.studentprojectportal.controller.common;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.ISubjectService;

@Controller
public class SidebarController {
    @Autowired
    ISubjectService subjectService;
    @GetMapping("/subject-manager/")
    public String sideBarList(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        model.addAttribute("subjectList", subjectService.findAllSubjectByUser(user));
        return "/component/dashboardSidebar";
    }
}
