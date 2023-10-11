package swp.studentprojectportal.controller.class_manager;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.ClassIssueSetting;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.IClassIssueSettingService;
import swp.studentprojectportal.service.servicesimpl.ClassService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ClassIssueSettingController {
    @Autowired
    IClassIssueSettingService classIssueSettingService;
    @Autowired
    ClassService classService;
    @GetMapping(path = "/class-manager/class-issue-setting")
    public String classIssueSettingPage(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
                                        @RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "-1") Integer status,
                                        @RequestParam(defaultValue = "-1") Integer classId,
                                        @RequestParam(defaultValue = "class_id") String sortBy, @RequestParam(defaultValue = "1") Integer sortType,
                                        Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Class> classList = classService.findClassForIssue(user.getId());
        Map<Integer, String> classMap = classList.stream()
                .collect(Collectors.toMap(Class::getId, Class::getClassName));
        //List<ClassIssueSetting> classIssueSettingList = classIssueSettingService.getALL(user.getId());
        //Page<ClassIssueSetting> classIssueSettingList = classIssueSettingService.filter(user.getId(),search,pageNo,pageSize,sortBy,sortType,status);
        Page<ClassIssueSetting> classIssueSettingList = classIssueSettingService.findAllByClassManagerId(user.getId(),search,pageNo,pageSize,sortBy,sortType,classId,status);
        model.addAttribute("classMap",classMap);
        model.addAttribute("classId",classId);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("status", status);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("totalPage", classIssueSettingList.getTotalPages());
        model.addAttribute("classList",classList);
        model.addAttribute("classIssueSettingList", classIssueSettingList);
        return "class_manager/class_issue_setting/classIssueSettingList";
    }
}
