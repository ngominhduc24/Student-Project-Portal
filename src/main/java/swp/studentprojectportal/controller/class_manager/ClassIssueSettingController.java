package swp.studentprojectportal.controller.class_manager;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.Assignment;
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

    @GetMapping("/class-manager/class-issue-setting/updateStatus")
    public String updateIssueSettingStatus(
            @RequestParam int id,
            @RequestParam boolean status) {
        ClassIssueSetting classIssueSetting = classIssueSettingService.findById(id);
        classIssueSetting.setStatus(status);
        classIssueSettingService.saveClassIssueSetting(classIssueSetting);
        return "redirect:/";
    }

    @GetMapping(path="/class-manager/class-issue-setting/detail")
    public String classIssueSettingDetail(@RequestParam("id") Integer id, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Class> classList = classService.findClassForIssue(user.getId());
        Map<Integer, String> classMap = classList.stream()
                .collect(Collectors.toMap(Class::getId, Class::getClassName));
        ClassIssueSetting classIssueSetting = classIssueSettingService.findById(id);
        model.addAttribute("classIssueSetting",classIssueSetting);
        model.addAttribute("classList",classList);
        model.addAttribute("classMap",classMap);
        return "class_manager/class_issue_setting/classIssueSettingDetail";
    }

    @PostMapping(path = "/class-manager/class-issue-setting/update")
    public String updateclassIssueSetting(@RequestParam Integer id, @RequestParam int classId, @RequestParam String description,
                                          @RequestParam String className, @RequestParam String type,
                                          @RequestParam String statusIssue, @RequestParam String workProcess,
                                          WebRequest request, Model model, HttpSession session){
        String status = request.getParameter("status");
        ClassIssueSetting classIssueSetting = new ClassIssueSetting();
        classIssueSetting.setStatus(Boolean.parseBoolean(status));
        classIssueSetting.setId(id);
        classIssueSetting.setDescription(description);
        classIssueSetting.setType(type);
        classIssueSetting.setStatusIssue(statusIssue);
        classIssueSetting.setWorkProcess(workProcess);
        classIssueSetting.setAClass(classService.findById(classId));

        classIssueSettingService.saveClassIssueSetting(classIssueSetting);

        model.addAttribute("classIssueSetting",classIssueSetting);
        User user = (User) session.getAttribute("user");
        List<Class> classList = classService.findClassForIssue(user.getId());
        Map<Integer, String> classMap = classList.stream()
                .collect(Collectors.toMap(Class::getId, Class::getClassName));
        model.addAttribute("classList",classList);
        model.addAttribute("classMap",classMap);
        return "class_manager/class_issue_setting/classIssueSettingDetail";
    }

    @GetMapping(path="/class-manager/class-issue-setting/add")
    public String addClassIssueSetttingPage(HttpSession session,Model model){
        ClassIssueSetting classIssueSetting = new ClassIssueSetting();
        classIssueSetting.setDescription("");
        classIssueSetting.setType("");
        classIssueSetting.setStatusIssue("");
        classIssueSetting.setWorkProcess("");
        classIssueSetting.setAClass(new Class());
        model.addAttribute("classIssueSetting",classIssueSetting);
        User user = (User) session.getAttribute("user");
        List<Class> classList = classService.findClassForIssue(user.getId());
        Map<Integer, String> classMap = classList.stream()
                .collect(Collectors.toMap(Class::getId, Class::getClassName));
        model.addAttribute("classList",classList);
        model.addAttribute("classMap",classMap);
        return "class_manager/class_issue_setting/classIssueSettingAdd";
    }

//    @PostMapping(path="/class-manager/class-issue-setting/add")
//    public String addClassIssueSetting(WebRequest request){
//        String classId = request.getParameter("classId");
//        if(classId!=null) System.out.println(classId);
//        else System.out.println("huhu");
//        System.out.println("halo");
//        return "class_manager/class_issue_setting/classIssueSettingAdd";
//    }

    @PostMapping(path="/class-manager/class-issue-setting/add")
    public String addClassIssueSetting(@RequestParam String description, @RequestParam String type,
                                       @RequestParam String statusIssue, @RequestParam String workProcess,
                                       @RequestParam int className,
                                       WebRequest request, Model model, HttpSession session){
        System.out.println("no1"+ description);
        System.out.println("no2"+ type);
        System.out.println("no3"+ statusIssue);
        System.out.println("no4"+ workProcess);
        System.out.println("no5"+ className);
        ClassIssueSetting classIssueSetting = new ClassIssueSetting();
        classIssueSetting.setDescription(description);
        classIssueSetting.setType(type);
        classIssueSetting.setStatusIssue(statusIssue);
        classIssueSetting.setWorkProcess(workProcess);
        classIssueSetting.setAClass(classService.findById(className));
        model.addAttribute("classIssueSetting",classIssueSetting);
        classIssueSettingService.saveClassIssueSetting(classIssueSetting);
        User user = (User) session.getAttribute("user");
        List<Class> classList = classService.findClassForIssue(user.getId());
        Map<Integer, String> classMap = classList.stream()
                .collect(Collectors.toMap(Class::getId, Class::getClassName));
        model.addAttribute("classNameSelected",className);
        model.addAttribute("classList",classList);
        model.addAttribute("classMap",classMap);
        return "class_manager/class_issue_setting/classIssueSettingAdd";
    }
}
