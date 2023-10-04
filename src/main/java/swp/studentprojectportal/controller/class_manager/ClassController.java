package swp.studentprojectportal.controller.class_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.service.servicesimpl.ClassService;

@Controller
@RequestMapping("/classmanager")
public class ClassController {
    @Autowired
    ClassService classService;

    @GetMapping("/class")
    public String userList(Model model,
                           @RequestParam(defaultValue = "-1") int classId) {
        System.out.println(classService.getAllStudent(classId));
        model.addAttribute("classId", classId);
        model.addAttribute("classList", classService.getAllStudent(classId));
        return "admin/user/userList";
    }
}
