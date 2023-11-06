package swp.studentprojectportal.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import swp.studentprojectportal.service.servicesimpl.SettingService;

@Controller
public class StudentHomeController {
    @Autowired
    SettingService settingService;

    @GetMapping("student/home")
    public String dashboard(Model model) {
        model.addAttribute("currentSemester", settingService.getLastestSemester().getSettingTitle());
        return "student/studentDashboard";
    }
}
