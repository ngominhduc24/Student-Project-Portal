package swp.studentprojectportal.controller.class_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.service.servicesimpl.SettingService;

@Controller
@RequestMapping("/classmanager")
public class ClassController {
    @Autowired
    ClassService classService;

    @GetMapping("/class")
    public String userList(Model model,
                           @RequestParam(defaultValue = "-1") int classId) {
        Class c = classService.getClass(classId);
        model.addAttribute("className", c.getClassName());
        model.addAttribute("semester", c.getSetting().getSettingTitle());
        model.addAttribute("studentList", classService.getAllStudent(classId));
        return "class_manager/studentList";
    }
}
