package swp.studentprojectportal.controller.subject_manager;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.IssueSetting;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.ISubjectRepository;
import swp.studentprojectportal.service.servicesimpl.IssueSettingService;
import swp.studentprojectportal.service.servicesimpl.SubjectService;

import java.util.List;
@Controller
public class IssueSettingController {
    @Autowired
    SubjectService subjectService;
    @Autowired
    IssueSettingService issueSettingService;
    @Autowired
    ISubjectRepository subjectRepository;

    @GetMapping("/issue-setting/updateStatus")
    public String updateSubjectSettingStatus(
            @RequestParam int id,
            @RequestParam boolean status) {
        IssueSetting issueSetting = issueSettingService.findById(id);
        issueSetting.setStatus(status);
        issueSettingService.saveSubjectSetting(issueSetting);
        return "redirect:/";
    }
    @GetMapping("/issue-setting/detail")
    public String detailIssueSetting(@RequestParam("id") Integer id, Model model, HttpSession session){
        System.out.println("id is "+id);
        User user = (User) session.getAttribute("user");
        List<Subject> subjectList = subjectService.findAllSubjectByUser(user);
        IssueSetting issueSetting = issueSettingService.findById(id);
        model.addAttribute("setting", issueSetting);
        model.addAttribute("subjectList",subjectList);
        return "subject_manager/issue_setting/issueSettingDetail";
    }


    @PostMapping("/subject-manager/issue-setting/update")
    public String updateSetting(
            @RequestParam Integer id,
            @RequestParam String settingGroup,
            @RequestParam String settingTitle,
            @RequestParam String settingDescription,
            WebRequest request, Model model, HttpSession session) {

        String status = request.getParameter("settingStatus");
        boolean isStatus = status != null && status.equals("on");
        System.out.println("status update is "+isStatus);
        //String settingDescription = request.getParameter("settingDescription");
//        IssueSetting issueSetting = new IssueSetting();
        IssueSetting issueSetting = issueSettingService.findById(id);
        issueSetting.setStatus(isStatus);
        issueSetting.setSettingGroup(settingGroup);
        issueSetting.setSettingTitle(settingTitle);
        issueSetting.setDescription(settingDescription);
        issueSettingService.saveSubjectSetting(issueSetting);
        model.addAttribute("setting",issueSetting);
        return "subject_manager/issue_setting/issueSettingDetail";
    }

    @GetMapping(path = "/issue-setting/add")
    public String addIssueSettingaPage(Model model,HttpSession session){
        IssueSetting issueSettingg = new IssueSetting();
        issueSettingg.setDescription("");
        issueSettingg.setSettingGroup("");
        issueSettingg.setSettingGroup("");
        model.addAttribute("setting",issueSettingg);

        User user = (User) session.getAttribute("user");
        List<Subject> subjectList = subjectService.findAllSubjectByUser(user);
        model.addAttribute("subjectList",subjectList);
        return  "subject_manager/issue_setting/issueSettingAdd";
    }

    @PostMapping("/issue-setting/add")
    public String addIssueSetting(
            @RequestParam Integer subjectId,
            @RequestParam String settingTitle,
            @RequestParam String settingGroup,
            @RequestParam String settingDescription,
            Model model, HttpSession session) {
        IssueSetting issueSetting = new IssueSetting();
        issueSetting.setSubject(subjectRepository.getById(subjectId));
        issueSetting.setSettingTitle(settingTitle);
        issueSetting.setSettingGroup(settingGroup);
        issueSetting.setDescription(settingDescription);
        issueSettingService.saveSubjectSetting(issueSetting);
        model.addAttribute("setting",issueSetting);

        User user = (User) session.getAttribute("user");
        List<Subject> subjectList = subjectService.findAllSubjectByUser(user);
        model.addAttribute("subjectList",subjectList);
        return "subject_manager/issue_setting/issueSettingAdd";
    }

}
