package swp.studentprojectportal.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import swp.studentprojectportal.service.servicesimpl.*;

@Controller
public class DashboardController {

    @Autowired
    SettingService settingService;
    @Autowired
    UserService userService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    ClassService classService;
    @Autowired
    ProjectService projectService;
    @Autowired
    MilestoneService milestoneService;

    @GetMapping("admin/home")
    public String dashboard(Model model) {
        model.addAttribute("currentSemester", settingService.getLastestSemester().getSettingTitle());
        model.addAttribute("studentCount", userService.countStudent());
        model.addAttribute("teacherCount", userService.countTeacher());
        model.addAttribute("subjectCount",subjectService.subjectCount());
        model.addAttribute("classCount", classService.classCount());
        model.addAttribute("projectCount", projectService.projectCount());
        model.addAttribute("milestoneCount", milestoneService.milestoneCount());
        return "admin/home";
    }

}
