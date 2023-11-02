package swp.studentprojectportal.controller.student;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.StudentClassService;

@Controller
public class ProjectController1 {
    @Autowired
    StudentClassService studentClassService;
    @GetMapping("student/project/list")
    public String projectList(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        System.out.println(studentClassService.findAllByStudentId(user.getId()));
        model.addAttribute("projectList", studentClassService.findAllByStudentId(user.getId()));
        return "student/projectList";
    }
}
