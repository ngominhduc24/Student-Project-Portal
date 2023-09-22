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
public class SubjectController {

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
        subjectList = subjectService.getSubject(0, 15);
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
            @RequestParam int subjectManagerId,
            Model model) {

        String errorMsg = checkValidate(subjectName, subjectCode, subjectManagerId);
        if(errorMsg!=null) {
            model.addAttribute("error", errorMsg);
            return "admin/subject/subjectAdd";
        }
        int newSubjectId = subjectService.addSubject(subjectName, subjectCode, subjectManagerId, true).getId();
        return "redirect:./subjectDetails?id=" + newSubjectId;
    }

    @GetMapping("/admin/subjectDetails")
    public String updateSubjectPage(@RequestParam("id") Integer Id, Model model) {
        Subject subject = subjectService.getSubjectById(Id);
        model.addAttribute("subject", subject);
        model.addAttribute("subjectManagerList", userService.findAllUserByRoleId(3));
        return "admin/subject/subjectDetail";
    }

    @PostMapping("/updateSubject")
    public String updateSubject(
            @RequestParam int id,
            @RequestParam String subjectName,
            @RequestParam String subjectCode,
            @RequestParam int subjectManagerId,
            @RequestParam boolean status,
            Model model) {

        String msg = checkValidateUpdate(subjectName, subjectCode, subjectManagerId);
        if (msg != null) {
            model.addAttribute("errorMsg", msg);
        } else {
            boolean ans = subjectService.updateSubject(id, subjectName, subjectCode, subjectManagerId, status);
            if(ans) model.addAttribute("successMsg", "Update success");
            else model.addAttribute("errorMsg", "Update failed");
        }
        return "admin/subject/subjectDetail";
    }
    @GetMapping("/admin/subject/updateStatus")
    public String updateSettingStatus(
            @RequestParam int id,
            @RequestParam boolean status) {
        subjectService.updateSubjectStatus(id, status);
        return "redirect:/";
    }

    private String checkValidate(String subjectName, String subjectCode, int subjectManagerId) {
        if(subjectName.isEmpty()) return "Please input subject name";
        if(subjectCode.isEmpty()) return "Please input subject code";
        if(subjectManagerId == 0) return "Please input subject manager";

        if(subjectService.checkSubjectNameExist(subjectName)) return "Subject name already exist";
        if(subjectService.checkSubjectCodeExist(subjectCode)) return "Subject code already exist";

        return null;
    }

    private String checkValidateUpdate(String subjectName, String subjectCode, int subjectManagerId) {
        if(subjectName.isEmpty()) return "Please input subject name";
        if(subjectCode.isEmpty()) return "Please input subject code";
        if(subjectManagerId == 0) return "Please input subject manager";

        if(subjectService.checkSubjectNameExist(subjectName)) return "Subject name already exist";
        if(subjectService.checkSubjectCodeExist(subjectCode)) return "Subject code already exist";

        return null;
    }
}




