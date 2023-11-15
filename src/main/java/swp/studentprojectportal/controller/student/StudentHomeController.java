package swp.studentprojectportal.controller.student;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Project;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.ProjectService;
import swp.studentprojectportal.service.servicesimpl.SettingService;

@Controller
public class StudentHomeController {
    @Autowired
    SettingService settingService;
    @Autowired
    ProjectService projectService;

    @GetMapping("student/home")
    public String dashboard(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "6") Integer pageSize,
                            Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Page<Project> projectList = projectService.filterByStudendDashboard(user.getId(), pageNo, pageSize);
        model.addAttribute("projects", projectList);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentSemester", settingService.getLastestSemester().getSettingTitle());
        model.addAttribute("totalPage", projectList.getTotalPages());
        return "student/studentDashboard";
    }
}
