package swp.studentprojectportal.controller.class_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.service.servicesimpl.SettingService;
import swp.studentprojectportal.service.servicesimpl.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/class-manager")
public class StudentController {
    @Autowired
    ClassService classService;
    @Autowired
    UserService userService;
    @Autowired
    SettingService settingService;

    @GetMapping("/class")
    public String studentList(Model model,
                           @RequestParam(defaultValue = "-1") int classId) {
        Class c = classService.getClass(classId);
        if(c == null) {
            return "redirect:/class";
        }
        model.addAttribute("className", c.getClassName());
        model.addAttribute("semester", c.getSemester().getSettingTitle());
        model.addAttribute("totalPage", userService.getTotalPage(10, 1));
        model.addAttribute("studentList", classService.getAllStudent(classId));
        return "class_manager/studentList";
    }

    @GetMapping("/class/studentDetails")
    public String studentDetails(Model model,
                           @RequestParam(defaultValue = "-1") int studentId) {
        Optional<User> user = userService.findUserById(studentId);
        model.addAttribute("user", user.isPresent() ? user.get() : null);
        model.addAttribute("roleList", settingService.getAllRole());
        return "class_manager/studentDetails";
    }
}
