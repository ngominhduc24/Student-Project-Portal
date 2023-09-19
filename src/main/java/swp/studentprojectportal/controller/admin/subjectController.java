package swp.studentprojectportal.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.repository.ISubjectRepository;
import swp.studentprojectportal.services.servicesimpl.ResetPassword;
import swp.studentprojectportal.services.servicesimpl.SubjectSevice;
import swp.studentprojectportal.services.servicesimpl.UserService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class subjectController {

    @Autowired
    SubjectSevice subjectService;
    @Autowired
    UserService userService;
    @Autowired
    ResetPassword resetPassword;

    List<Subject> subjectList = new CopyOnWriteArrayList<>();

    @GetMapping("/admin/subject")
    public String subjectPage(Model model) {
        subjectList = subjectService.getAllSubjects();
        model.addAttribute("SubjectList", subjectList);
        return "admin/subjectList";
    }

    @GetMapping("/admin/subject/add")
    public String createSubjectPage(Model model) {
        model.addAttribute("subject", new Subject());
        return "admin/subjectAdd";
    }

    @PostMapping("/admin/subject/add")
    public Subject createSubject(@RequestBody Subject subject) {
        return subjectService.saveSubject(subject);
    }

    @GetMapping("/admin/subjectDetails")
    public String updateSubjectPage(@RequestParam("id") Integer Id, Model model) {
        Subject subject = subjectService.getSubjectById(Id);
        model.addAttribute("subject", subject);
        model.addAttribute("subjectManagerList", userService.findAllUserByRoleId(3));
        return "admin/subjectDetail";
    }

    @PostMapping("/admin/updateSubject")
    public String updateSubject(
            @RequestParam int id,
            @RequestParam String subjectName,
            @RequestParam String subjectCode,
            @RequestParam int subjectManagerId,
            @RequestParam boolean status){
        System.out.println(id + subjectName + subjectName + subjectManagerId + status);
        subjectService.updateSubject(id, subjectName, subjectCode, subjectManagerId, status);
        return "redirect:./subject";
    }

    @GetMapping("/admin/subject/updateStatus")
    public String updateSettingStatus(
            @RequestParam int id,
            @RequestParam boolean status) {
        subjectService.updateSubjectStatus(id, status);
        return "redirect:/";
    }

}




