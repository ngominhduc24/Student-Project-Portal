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
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.Setting;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.service.servicesimpl.SettingService;
import swp.studentprojectportal.service.servicesimpl.SubjectSevice;
import swp.studentprojectportal.service.servicesimpl.UserService;

import java.util.List;

@Controller
@RequestMapping("/class-manager")
public class ClassCController {
    @Autowired
    ClassService classService;
    @Autowired
    SubjectSevice subjectService;
    @Autowired
    UserService userService;
    @Autowired
    SettingService settingService;

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
            @RequestParam boolean status) {
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
    public String updateClass(@RequestParam Integer id,
                              WebRequest request, Model model) {
        String status = request.getParameter("status");
        Class classA = new Class();
        classA.setId(id);
        classService.saveClass(classA);
        model.addAttribute("msg", "Successfully");
        return "class_manager/class/classDetail";
    }
}
