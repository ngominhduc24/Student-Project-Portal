package swp.studentprojectportal.controller.subject_manager;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.*;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.service.servicesimpl.SettingService;
import swp.studentprojectportal.service.servicesimpl.SubjectSevice;
import swp.studentprojectportal.service.servicesimpl.UserService;

import java.util.List;
@Controller
public class ClassController {
    @Autowired
    ClassService classService;
    @Autowired
    SubjectSevice subjectService;
    @Autowired
    UserService userService;
    @Autowired
    SettingService settingService;
    @GetMapping("/subject-manager/class")
    public String classPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Class> classList = classService.findAllBySubjectManagerId(user.getId());
        List<Subject> subjectList = subjectService.findAllSubjectByUser(user);
        List<User> teacherList = userService.findTeacherBySubjectManagerId(user.getId());
        List<Setting> semesterList = settingService.findSemesterBySubjectManagerId(user.getId());
        model.addAttribute("classList", classList);
        model.addAttribute("subjectList",subjectList);
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("semesterList",semesterList);
        return "subject_manager/class/classList";
    }

    @GetMapping("/subject-manager/class/updateStatus")
    public String updateSubjectSettingStatus(
            @RequestParam int id,
            @RequestParam boolean status) {
        Class classA = classService.findById(id);
        classA.setStatus(status);
        classService.saveClass(classA);
        return "redirect:/";
    }
}
