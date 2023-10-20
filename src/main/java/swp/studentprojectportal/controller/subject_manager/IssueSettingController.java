package swp.studentprojectportal.controller.subject_manager;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.IssueSetting;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.ISubjectRepository;
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.service.servicesimpl.IssueSettingService;
import swp.studentprojectportal.service.servicesimpl.SubjectService;
import swp.studentprojectportal.utils.Validate;

import java.util.List;
@Controller
public class IssueSettingController {
    @Autowired
    SubjectService subjectService;
    @Autowired
    ClassService classService;
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
    @GetMapping("/subject-manager/issue-setting/detail")
    public String detailIssueSetting(@RequestParam("id") Integer id, Model model, HttpSession session){
        System.out.println("id is "+id);
        User user = (User) session.getAttribute("user");
        List<Subject> subjectList = subjectService.findAllSubjectByUser(user);
        IssueSetting issueSetting = issueSettingService.findById(id);
        model.addAttribute("setting", issueSetting);
        model.addAttribute("subjectList",subjectList);
        return "subject_manager/issue_setting/issueSettingDetail";
    }

    @GetMapping("/class-manager/issue-setting/detail")
    public String detailClassIssueSetting(@RequestParam("id") Integer id, Model model, HttpSession session){
        System.out.println("id is "+id);
        User user = (User) session.getAttribute("user");
        IssueSetting issueSetting = issueSettingService.findById(id);
        model.addAttribute("setting", issueSetting);
        List<Class> classList = classService.findAllByClassManagerId(user.getId());
        model.addAttribute("classList",classList);
        return "subject_manager/issue_setting/issueSettingClassDetail";
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
        IssueSetting issueSetting = issueSettingService.findById(id);
        issueSetting.setStatus(isStatus);
        issueSetting.setSettingGroup(settingGroup);
        issueSetting.setSettingTitle(settingTitle);
        issueSetting.setDescription(settingDescription);
        issueSettingService.saveSubjectSetting(issueSetting);
        model.addAttribute("setting",issueSetting);
        return "subject_manager/issue_setting/issueSettingDetail";
    }

    @PostMapping("/class-manager/issue-setting/update")
    public String updateIssueSetting(
            @RequestParam Integer id,
            @RequestParam Integer classId,
            @RequestParam String settingGroup,
            @RequestParam String settingTitle,
            @RequestParam String description,
            WebRequest request, Model model, HttpSession session) {

        String status = request.getParameter("settingStatus");
        boolean isStatus = status != null && status.equals("on");
        IssueSetting issueSetting = issueSettingService.findById(id);
        issueSetting.setStatus(isStatus);
        issueSetting.setSettingGroup(settingGroup);
        issueSetting.setSettingTitle(settingTitle);
        issueSetting.setDescription(description);
        issueSetting.setAclass(classService.findById(classId));

        if(Validate.validNotempty(settingGroup) == false){
            String errmsg = "Group can't empty. Update failed!";
            model.addAttribute("errmsg",errmsg);
        }
        else{
            IssueSetting findIssueSetting = issueSettingService.findByClassAndGroupAndTitle(classId,settingGroup,settingTitle);

            if(findIssueSetting!=null && findIssueSetting.getId() != id){
                String errmsg = "Issue setting existed. Update failed!";
                model.addAttribute("errmsg",errmsg);
            }
            else {
                issueSettingService.saveSubjectSetting(issueSetting);
                String msg = "Update successfully!";
                model.addAttribute("msg", msg);
            }
        }


        model.addAttribute("setting",issueSetting);
        User user = (User) session.getAttribute("user");
        List<Class> classList = classService.findAllByClassManagerId(user.getId());
        model.addAttribute("classList",classList);
        return "subject_manager/issue_setting/issueSettingClassDetail";
    }

    @GetMapping(path = "/subject-manager/issue-setting/add")
    public String addIssueSettingPage(Model model,HttpSession session){
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

    @GetMapping(path = "/class-manager/issue-setting/add")
    public String addIssueSettingPage2(@RequestParam("id") Integer classId, Model model,HttpSession session){
        System.out.println("classsID is "+ classId);
        IssueSetting issueSettingg = new IssueSetting();
        issueSettingg.setDescription("");
        issueSettingg.setSettingGroup("");
        issueSettingg.setSettingGroup("");
        issueSettingg.setAclass(classService.findById(classId));
        model.addAttribute("setting",issueSettingg);
        model.addAttribute("classId",classId);
        User user = (User) session.getAttribute("user");
        List<Class> classList = classService.findAllByClassManagerId(user.getId());

        model.addAttribute("classList",classList);
        return  "subject_manager/issue_setting/issueSettingClassAdd";
    }

    @PostMapping("/subject-manager/issue-setting/add")
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

    @PostMapping("/class-manager/issue-setting/add")
    public String addIssueSetting2(
            @RequestParam Integer classId,
            @RequestParam String settingTitle,
            @RequestParam String settingGroup,
            @RequestParam String description,
            Model model, HttpSession session) {
        IssueSetting issueSetting = new IssueSetting();
        issueSetting.setAclass(classService.findById(classId));
        issueSetting.setSettingTitle(settingTitle);
        issueSetting.setSettingGroup(settingGroup);
        issueSetting.setDescription(description);

        if(Validate.validNotempty(settingGroup) == false){
            String errmsg = "Group can't empty. Add failed!";
            model.addAttribute("errmsg",errmsg);
        }
        else{
            if(issueSettingService.findByClassAndGroupAndTitle(classId,settingGroup,settingTitle)!=null){
                String errmsg = "Issue setting existed. Add failed!";
                model.addAttribute("errmsg",errmsg);
            }
            else {
                issueSettingService.saveSubjectSetting(issueSetting);
                String msg = "Add successfully!";
                model.addAttribute("msg", msg);
            }
        }

        model.addAttribute("setting",issueSetting);
        model.addAttribute("classId",classId);
        return "subject_manager/issue_setting/issueSettingClassAdd";
    }

}
