package swp.studentprojectportal.controller.class_manager;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.ClassIssueSetting;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.IClassIssueSettingService;

import java.util.List;

@Controller
public class ClassIssueSettingController {
    @Autowired
    IClassIssueSettingService classIssueSettingService;
    @GetMapping(path = "/class-manager/class-issue-setting")
    public String classIssueSettingPage(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
                                        @RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "-1") Integer status,
                                        @RequestParam(defaultValue = "subject_id") String sortBy, @RequestParam(defaultValue = "1") Integer sortType,
                                        Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<ClassIssueSetting> classIssueSettingList = classIssueSettingService.getALL(user.getId());
        //Page<ClassIssueSetting> classIssueSettingList = classIssueSettingService.filter(user.getId(),search,pageNo,pageSize,sortBy,sortType,status);
        model.addAttribute("classIssueSettingList", classIssueSettingList);
        return "class_manager/class_issue_setting/classIssueSettingList";
    }
}
