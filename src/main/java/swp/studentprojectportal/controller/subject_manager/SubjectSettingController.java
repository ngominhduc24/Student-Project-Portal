package swp.studentprojectportal.controller.subject_manager;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
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
    @RequestMapping("/subject-manager/subject-setting/detail")
    public String detailSubjectSetting(@RequestParam("settingID") int id, Model model){

//        Integer id = Integer.parseInt( request.getParameter("id") );
        System.out.println("id="+id);
        System.out.println("noway");
        SubjectSetting subjectSetting = subjectSettingService.findById(id);
        model.addAttribute("setting",subjectSetting);
//        return "subject_manager/subject_setting/subjectSettingDetail";
        return "test";
    }

    @PostMapping(path = "/subject-manager/subject-setting/update")
    public  String updateSubjectSetting(WebRequest request,Model model){
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        String title = request.getParameter("title");
        String displayOrder = request.getParameter("displayOrder");
        System.out.println(id);
        System.out.println(type);
        System.out.println(title);
        System.out.println(displayOrder);

        SubjectSetting subjectSetting = subjectSettingService.findById(Integer.parseInt(id));
        subjectSetting.setTypeId(Integer.parseInt(type));
        subjectSetting.setSettingTitle(title);
        subjectSetting.setDisplayOrder(Integer.parseInt(displayOrder));
        subjectSettingService.saveSubjectSetting(subjectSetting);
        model.addAttribute("setting",subjectSetting);


        return "test";
    }

    @RequestMapping(path="/test")
    public  String testNTN(@RequestParam("settingID") int settingId){
        System.out.println("hehe "+settingId);
        return "redirect:/profile";
    }


}
