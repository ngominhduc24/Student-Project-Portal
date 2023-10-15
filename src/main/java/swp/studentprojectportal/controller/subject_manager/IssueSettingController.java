package swp.studentprojectportal.controller.subject_manager;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @RequestMapping("/issue-setting/detail")
    public String detailSubjectSetting(@RequestParam("id") int id, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Subject> subjectList = subjectService.findAllSubjectByUser(user);
        IssueSetting issueSetting = issueSettingService.findById(id);
        model.addAttribute("setting", issueSetting);
        model.addAttribute("subjectList",subjectList);
        return "subject_manager/issue_setting/issueSettingDetail";
    }

//    @PostMapping("/subject-manager/subject-setting/update")
//    public String updateSetting(
//            @RequestParam Integer id,
//            @RequestParam Integer subjectId,
//            @RequestParam Integer typeId,
//            @RequestParam String settingTitle,
//            @RequestParam Integer displayOrder,
//            WebRequest request,Model model,HttpSession session) {

//        if(settingTitle.trim().isEmpty()){
//            model.addAttribute("errmsg","Title not empty!");
//            User user = (User) session.getAttribute("user");
//            List<Subject> subjectList = subjectService.findAllSubjectByUser(user);
//            SubjectSetting subjectSetting = subjectSettingService.findById(id);
//            model.addAttribute("setting",subjectSetting);
//            model.addAttribute("subjectList",subjectList);
//            return "subjectSettingDetail";
//        }
//        String status = request.getParameter("status");
//        IssueSetting issueSetting = new IssueSetting();
//        issueSetting.setId(id);
//        issueSetting.setSubject(subjectRepository.getById(subjectId));
//        issueSetting.setTypeId(typeId);
//        issueSetting.setSettingTitle(settingTitle);
//        issueSetting.setDisplayOrder(displayOrder);
//        issueSetting.setStatus(status!=null);
//        issueSettingService.saveSubjectSetting(issueSetting);
//        return "redirect:/subject-manager/subject-setting";
//    }

    @RequestMapping(path = "/issue-setting/add")
    public String addSubjectSettingaPage(Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Subject> subjectList = subjectService.findAllSubjectByUser(user);
        model.addAttribute("subjectList",subjectList);
        return  "subject_manager/issue_setting/issueSettingAdd";
    }

//    @PostMapping("/subject-manager/subject-setting/add")
//    public String addSetting(
//            @RequestParam Integer subjectId,
//            @RequestParam Integer typeId,
//            @RequestParam String settingTitle,
//            @RequestParam Integer displayOrder,
//            WebRequest request) {
//        String status = request.getParameter("status");
//        IssueSetting issueSetting = new IssueSetting();
//        issueSetting.setSubject(subjectRepository.getById(subjectId));
//        issueSetting.setTypeId(typeId);
//        issueSetting.setSettingTitle(settingTitle);
//        issueSetting.setDisplayOrder(displayOrder);
//        issueSetting.setStatus(status!=null);
//        issueSettingService.saveSubjectSetting(issueSetting);
//        return "redirect:/subject-manager/subject-setting";
//    }

}
