package swp.studentprojectportal.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import swp.studentprojectportal.services.servicesimpl.SettingService;

@Controller
public class dashboardController {

    @Autowired
    SettingService settingService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("currentSemester", settingService.getLastestSemester().getSettingTitle());
        return "dashboard";
    }

}
