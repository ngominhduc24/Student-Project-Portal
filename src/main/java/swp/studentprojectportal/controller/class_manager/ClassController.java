package swp.studentprojectportal.controller.class_manager;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import swp.studentprojectportal.model.*;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.service.servicesimpl.*;

import java.util.List;

@Controller
@RequestMapping("/class-manager")
public class ClassController {
    @Autowired
    ClassService classService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    UserService userService;
    @Autowired
    SettingService settingService;
    @Autowired
    StudentClassService studentClassService;

    @GetMapping("/classList")
    public String classPage(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
                @RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "-1") Integer subjectId,
                @RequestParam(defaultValue = "-1") Integer semesterId, @RequestParam(defaultValue = "-1") Integer status,
                @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "1") Integer sortType,
                            Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Page<Class> classList = classService.findAllByClassManagerId(user.getId(), search, pageNo, pageSize, sortBy, sortType, subjectId, semesterId, status);
        List<Subject> subjectList = subjectService.findAllSubjectByClassManagerId(user.getId());
        List<Setting> semesterList = settingService.findSemesterByClassManagerId(user.getId());
        model.addAttribute("classList", classList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("subjectId", subjectId);
        model.addAttribute("semesterId", semesterId);
        model.addAttribute("status", status);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("totalPage", classList.getTotalPages());
        model.addAttribute("subjectList",subjectList);
        model.addAttribute("semesterList",semesterList);
        return "class_manager/class/classList";
    }

    @GetMapping("/class/updateStatus")
    public String updateSubjectSettingStatus(
            @RequestParam int id,
            @RequestParam Integer status) {
        Class classA = classService.findById(id);
        classA.setStatus(status);
        classService.saveClass(classA);
        return "redirect:/";
    }

    @GetMapping("/classDetail")
    public String classDetail(@RequestParam("id") Integer id, Model model) {
        Class classA = classService.findById(id);
        model.addAttribute("class", classA);
        return "class_manager/class/classDetail";
    }

    @PostMapping("/class/update")
    public String updateClass(@RequestParam Integer id, @RequestParam String description,
                              WebRequest request, Model model, RedirectAttributes attributes) {
        Class classA = classService.findById(id);
        classA.setDescription(description);
        classService.saveClass(classA);
        attributes.addFlashAttribute("toastMessage", "Update class description successfully");
        return "redirect:/class-manager/classList";
    }
}
