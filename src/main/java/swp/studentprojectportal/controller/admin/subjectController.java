package swp.studentprojectportal.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import swp.studentprojectportal.model.Subject;
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

    List<Subject> subjectList = new CopyOnWriteArrayList<>();
    private boolean isSubjectAdded(String subjectName, String subjectCode, int subjectManagerId) {
        if(subjectName == null || subjectCode == null || subjectManagerId == 0) {
            return false;
        }
        return true;
    }

    @GetMapping("/admin/subject")
    public String subjectPage(Model model) {
        subjectList = subjectService.getAllSubjects();
        model.addAttribute("SubjectList", subjectList);
        return "admin/subject/subjectList";
    }

    @GetMapping("/admin/subjectAdd")
    public String createSubjectPage(Model model) {
        model.addAttribute("subject", new Subject());
        model.addAttribute("subjectManagerList", userService.findAllUserByRoleId(3));
        return "admin/subject/subjectAdd";
    }

    @PostMapping("/admin/addSubject")
    public String createSubject(
            @RequestParam String subjectName,
            @RequestParam String subjectCode,
            @RequestParam int subjectManagerId) {
        subjectService.addSubject(subjectName, subjectCode, subjectManagerId, true);
//        boolean isSubjectAdded = isSubjectAdded(subjectName, subjectCode, subjectManagerId);

        if ((subjectCode == null && subjectName == null) ||
                (subjectCode == null && !subjectService.checkSubjectCodeExist(subjectCode)) ||
                (subjectName == null && !subjectService.checkSubjectNameExist(subjectName)) ||
                (subjectManagerId == 0 && userService.findUserById(subjectManagerId).isEmpty())) {
            return "redirect:/admin/subjectAdd";
        }

        return "redirect:./subject";
    }

    @GetMapping("/admin/subjectDetails")
    public String updateSubjectPage(@RequestParam("id") Integer Id, Model model) {
        Subject subject = subjectService.getSubjectById(Id);
        model.addAttribute("subject", subject);
        model.addAttribute("subjectManagerList", userService.findAllUserByRoleId(3));
        return "admin/subject/subjectDetail";
    }

    @PostMapping("/admin/updateSubject")
    public String updateSubject(
            @RequestParam int id,
            @RequestParam String subjectName,
            @RequestParam String subjectCode,
            @RequestParam int subjectManagerId,
            @RequestParam boolean status){
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




