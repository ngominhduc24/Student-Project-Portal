package swp.studentprojectportal.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.service.servicesimpl.SubjectSevice;
import swp.studentprojectportal.service.servicesimpl.UserService;
import swp.studentprojectportal.utils.Validate;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class SubjectController {

    @Autowired
    SubjectSevice subjectService;

    @Autowired
    UserService userService;

    List<Subject> subjectList = new CopyOnWriteArrayList<>();

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
            WebRequest request,
            Model model) {
        String subjectName = Objects.requireNonNull(request.getParameter("subjectName")).trim();
        String subjectCode = Objects.requireNonNull(request.getParameter("subjectCode")).trim();
        int subjectManagerId = Integer.parseInt(Objects.requireNonNull(request.getParameter("subjectManagerId")));

        String errorMsg = checkValidateSubject(subjectName, subjectCode);
        if(errorMsg!=null) {
            model.addAttribute("errorMsg", errorMsg);
            model.addAttribute("subjectManagerList", userService.findAllUserByRoleId(3));
            return "/admin/subject/subjectAdd";
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

    @PostMapping("/admin/updateSubject")
    public String updateSubject(
            WebRequest request,
            Model model) {

        int id = Integer.parseInt(Objects.requireNonNull(request.getParameter("id")));
        String subjectName = Objects.requireNonNull(request.getParameter("subjectName")).trim();
        String subjectCode = Objects.requireNonNull(request.getParameter("subjectCode")).trim();
        int subjectManagerId = Integer.parseInt(Objects.requireNonNull(request.getParameter("subjectManagerId")));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));

        String msg = checkValidateUpdateSubject(subjectName, subjectCode, subjectManagerId, subjectService.getSubjectById(id));
        if (msg != null) {
            model.addAttribute("errorMsg", msg);
        } else {
            boolean ans = subjectService.updateSubject(id, subjectName, subjectCode, subjectManagerId, status);
            if (ans) model.addAttribute("successMsg", "Update success");
            else model.addAttribute("errorMsg", "Update failed");
        }

        model.addAttribute("subject", subjectService.getSubjectById(id));
        model.addAttribute("subjectManagerList", userService.findAllUserByRoleId(3));

        return "admin/subject/subjectDetail";
    }
    @GetMapping("/admin/subject/updateStatus")
    public String updateSettingStatus(
            @RequestParam int id,
            @RequestParam boolean status) {
        subjectService.updateSubjectStatus(id, status);
        return "redirect:/";
    }

    private String checkValidateSubject(String subjectName, String subjectCode) {
        if(subjectName.isEmpty()) return "Please input subject name";
        if(subjectCode.isEmpty()) return "Please input subject code";

        if(subjectService.checkSubjectNameExist(subjectName)) return "Subject name already exist";
        if(subjectService.checkSubjectCodeExist(subjectCode)) return "Subject code already exist";

        return null;
    }

    private String checkValidateUpdateSubject(String subjectName, String subjectCode, int subjectManagerId, Subject subject) {
        if(subjectName.isEmpty()) return "Please input subject name";
        if(subjectCode.isEmpty()) return "Please input subject code";
        if(subjectManagerId == 0) return "Please input subject manager";

        if(!subject.getSubjectName().equals(subjectName) && subjectService.checkSubjectNameExist(subjectName)) return "Subject name already exist";
        if(!subject.getSubjectCode().equals(subjectCode) && subjectService.checkSubjectCodeExist(subjectCode)) return "Subject code already exist";

        return null;
    }
}




