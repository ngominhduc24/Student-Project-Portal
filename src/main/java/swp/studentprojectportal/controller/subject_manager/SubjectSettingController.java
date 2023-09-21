package swp.studentprojectportal.controller.subject_manager;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Setting;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.SubjectSetting;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.ISubjectRepository;
import swp.studentprojectportal.services.servicesimpl.SubjectSettingService;

import java.util.List;
@Controller
public class SubjectSettingController {
    @Autowired
    ISubjectRepository subjectRepository;
    @Autowired
    SubjectSettingService subjectSettingService;
    @GetMapping("/subject-manager/subject-setting")
    public String settingPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Subject subject = subjectRepository.findSubjectByUser(user);
        List<SubjectSetting> subjectSettingList = subjectSettingService.findSubjectSettingBySubjectAndTypeIdOrderByDisplayOrder(subject,1);
        List<SubjectSetting> qualityList = subjectSettingService.findSubjectSettingBySubjectAndTypeIdOrderByDisplayOrder(subject,2);
        subjectSettingList.addAll(qualityList);
        model.addAttribute("subjectSettingList", subjectSettingList);
        return "subject_manager/subject_setting/subjectSettingList";
    }

    @GetMapping("/subject-manager/subject-setting/updateStatus")
    public String updateSubjectSettingStatus(
            @RequestParam int id,
            @RequestParam boolean status) {
        SubjectSetting subjectSetting = subjectSettingService.findById(id);
        subjectSetting.setStatus(status);
        subjectSettingService.saveSubjectSetting(subjectSetting);
        return "redirect:/";
    }
}
