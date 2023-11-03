package swp.studentprojectportal.controller.student;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.IssueService;
import swp.studentprojectportal.service.servicesimpl.StudentClassService;

@Controller
public class StudentIssueController {
    @Autowired
    IssueService issueService;

    @GetMapping("student/issue/list")
    public String IssueList(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("issueList", issueService.getAllIssueByStudentId(user.getId()));
        return "student/issueList";
    }
}
