package swp.studentprojectportal.controller.subject_manager;

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
import swp.studentprojectportal.model.*;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.service.servicesimpl.SettingService;
import swp.studentprojectportal.service.servicesimpl.SubjectService;
import swp.studentprojectportal.service.servicesimpl.UserService;

import java.util.List;
@Controller
@RequestMapping("/subject-manager")
public class ClassController {
    @Autowired
    ClassService classService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    UserService userService;
    @Autowired
    SettingService settingService;
    @GetMapping("/class")
    public String classPage(@RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "-1") Integer subjectId, @RequestParam(defaultValue = "-1") Integer semesterId,
            @RequestParam(defaultValue = "-1") Integer teacherId, @RequestParam(defaultValue = "-1") Integer status,
            @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "1") Integer sortType,
                            Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Page<Class> classList = classService.findAllBySubjectManagerId(user.getId(), search, pageNo, pageSize, sortBy, sortType, subjectId, semesterId, teacherId, status);
        List<Subject> subjectList = subjectService.findAllSubjectByUser(user);
        List<User> teacherList = userService.findTeacherBySubjectManagerId(user.getId());
        List<Setting> semesterList = settingService.findSemesterBySubjectManagerId(user.getId());
        model.addAttribute("classList", classList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("subjectId", subjectId);
        model.addAttribute("semesterId", semesterId);
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("status", status);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("totalPage", classList.getTotalPages());
        model.addAttribute("subjectList",subjectList);
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("semesterList",semesterList);
        return "subject_manager/class/classList";
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
    public String classDetail(@RequestParam("id") Integer id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Class classA = classService.findById(id);
        List<Subject> subjectList = subjectService.findAllSubjectByUserAndStatus(user, true);
        List<Setting> semesterList = settingService.findSemesterByStatus(3, true);
        List<User> teacherList = userService.findTeacherByRoleIdAndStatus(4, true);
        model.addAttribute("subjectList",subjectList);
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("semesterList",semesterList);
        model.addAttribute("class", classA);
        return "subject_manager/class/classDetail";
    }

    @PostMapping("/class/update")
    public String updateClass(@RequestParam Integer id,@RequestParam String description,
            @RequestParam String className, @RequestParam Integer subjectId,
            @RequestParam Integer semesterId, @RequestParam Integer classManagerId,
                              WebRequest request, Model model, HttpSession session) {
        String status = request.getParameter("status");
        Class classA = new Class();
        classA.setId(id);
        classA.setClassName(className);
        classA.setDescription(description);
        classA.setSubject(subjectService.getSubjectById(subjectId));
        classA.setSemester(settingService.getSettingByID(semesterId));
        classA.setUser(userService.getUserById(classManagerId));
        model.addAttribute("class", classA);
        if(classService.checkExistedClassName(className, subjectId, id))
            model.addAttribute("errmsg", "This class name has already existed!");
        else {
            classService.saveClass(classA);
            model.addAttribute("msg", "Successfully");
        }
        User user = (User) session.getAttribute("user");
        List<Subject> subjectList = subjectService.findAllSubjectByUserAndStatus(user, true);
        List<Setting> semesterList = settingService.findSemesterByStatus(3, true);
        List<User> teacherList = userService.findTeacherByRoleIdAndStatus(4, true);
        model.addAttribute("subjectList",subjectList);
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("semesterList",semesterList);
        return "subject_manager/class/classDetail";
    }
}
