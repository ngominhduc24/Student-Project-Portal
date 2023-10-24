package swp.studentprojectportal.controller.subject_manager;

import jakarta.servlet.http.HttpSession;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.IssueSetting;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.ISubjectRepository;
import swp.studentprojectportal.service.servicesimpl.*;
import swp.studentprojectportal.utils.Validate;
import swp.studentprojectportal.utils.dto.Mapper;

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
    @Autowired
    GitlabApiService gitlabApiService;
    @Autowired
    UserService userService;

    @GetMapping("/class/issue-setting")
    public String issueSettingPage(@RequestParam("id") Integer classId,@RequestParam(defaultValue = "0") Integer pageNo,
                                   @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String search,
                                   @RequestParam(defaultValue = "-1") Integer status, @RequestParam(defaultValue = "id") String sortBy,
                                   @RequestParam(defaultValue = "1") Integer sortType, @RequestParam(defaultValue = "") String settingGroup,
                                   Model model, HttpSession session) {
        Class classA = classService.findById(classId);
        Page<IssueSetting> issueSettingList= issueSettingService.filterClassIssueSetting(classA.getSubject().getId(), classId,search, pageNo, pageSize, sortBy, sortType, settingGroup, status);
        List<String> settingGroupList = issueSettingService.findAllDistinctClassSettingGroup(classA.getSubject().getId(), classId);

        IssueSetting setting = new IssueSetting();
        setting.setAclass(classA);
        setting.setSettingGroup("Not Empty");
        setting.setSettingTitle("");
        setting.setDescription("");
        model.addAttribute("setting",setting);

        User user = (User) session.getAttribute("user");
        model.addAttribute("subjectList", subjectService.findAllSubjectByUserAndStatus(user, true));
        model.addAttribute("personalToken", user.getPersonalTokenGitlab());
        model.addAttribute("class", classA);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("classId", classId);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("status", status);
        model.addAttribute("totalPage", issueSettingList.getTotalPages());
        model.addAttribute("settingGroup", settingGroup);

        model.addAttribute("issueSettingList", issueSettingList);
        model.addAttribute("settingGroupList", settingGroupList);

        return "class_manager/class/issueSettingList";
    }

    @PostMapping("/class/issue-setting")
    public String issueSettingAdd(@RequestParam("id") Integer classId,@RequestParam(defaultValue = "0") Integer pageNo,
                                  @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String search,
                                  @RequestParam(defaultValue = "-1") Integer status, @RequestParam(defaultValue = "id") String sortBy,
                                  @RequestParam(defaultValue = "1") Integer sortType, @RequestParam(defaultValue = "") String settingGroup,
                                  @RequestParam String description,
                                  @RequestParam("newSettingGroup") String newSettingGroup,
                                  @RequestParam("newSettingTitle") String newSettingTitle,
                                  Model model, HttpSession session) {
        Class classA = classService.findById(classId);
        Page<IssueSetting> issueSettingList= issueSettingService.filterClassIssueSetting(classA.getSubject().getId(), classId,search, pageNo, pageSize, sortBy, sortType, settingGroup, status);
        List<String> settingGroupList = issueSettingService.findAllDistinctClassSettingGroup(classA.getSubject().getId(), classId);

        IssueSetting setting = new IssueSetting();
        setting.setAclass(classA);
        setting.setSettingGroup(newSettingGroup);
        setting.setSettingTitle(newSettingTitle);
        setting.setDescription(description);
        if(newSettingGroup.equals("Not Empty") == false) {
            if (Validate.validNotempty(newSettingGroup) == false) {
                String errmsg = "Group can't empty. Add failed!";
                model.addAttribute("errmsg", errmsg);
            } else {
                if (issueSettingService.findByClassAndGroupAndTitle(classId, newSettingGroup, newSettingTitle) != null) {
                    String errmsg = "Issue setting existed. Add failed!";
                    model.addAttribute("errmsg", errmsg);
                } else {
                    issueSettingService.saveSubjectSetting(setting);
                    setting = new IssueSetting();
                    setting.setAclass(classA);
                    setting.setSettingGroup("Not Empty");
                    setting.setSettingTitle("");
                    setting.setDescription("");
                    model.addAttribute("toastMessage", "Add new issue setting successfully");
                }
            }
        }

        model.addAttribute("setting",setting);



        User user = (User) session.getAttribute("user");
        model.addAttribute("subjectList", subjectService.findAllSubjectByUserAndStatus(user, true));
        model.addAttribute("personalToken", user.getPersonalTokenGitlab());
        model.addAttribute("class", classA);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("classId", classId);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("status", status);
        model.addAttribute("totalPage", issueSettingList.getTotalPages());
        model.addAttribute("settingGroup", settingGroup);

        model.addAttribute("issueSettingList", issueSettingList);
        model.addAttribute("settingGroupList", settingGroupList);

        return "class_manager/class/issueSettingList";
    }


    @GetMapping("/class/issue-setting/updateStatus")
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

    @GetMapping("/issue-setting/sync-gitlab")
    public String synchronizeGitlabIssueSettingClass(
            @RequestParam(name = "classId", defaultValue = "-1") Integer classId,
            @RequestParam(name = "group") String groupIdOrPath,
            @RequestParam(name = "personalToken") String personalToken,
            HttpSession session
    ) throws GitLabApiException {
        List<Label> labelListGitlab =  gitlabApiService.getClassLabelGitlab(groupIdOrPath, personalToken);
        List<IssueSetting> labelListDB = issueSettingService.findAllSettingServiceByClassId(classId);

        // sync to db
        for (Label label : labelListGitlab) {
            boolean isExist = false;
            for (IssueSetting issueSetting : labelListDB) {
                if (Mapper.labelEquals(issueSetting, label)) {
                    isExist = true;
                    break;
                }
            }

            if (!isExist) {
                System.out.println(Mapper.labelConvert(label));
                IssueSetting issueSetting = Mapper.labelConvert(label);
                issueSetting.setAclass(classService.findById(classId));
                issueSettingService.saveSubjectSetting(issueSetting);
            }
        }

        // sync to gitlab
        for (IssueSetting issueSetting : labelListDB) {
            boolean isExist = false;
            for (Label label : labelListGitlab) {
                if (Mapper.labelEquals(issueSetting, label)) {
                    isExist = true;
                    break;
                }
            }

            if (!isExist) {
                Label label = Mapper.labelConvert(issueSetting);
                gitlabApiService.createClassLabel(groupIdOrPath, personalToken, label);
            }
        }
        // save personal token
        User user = (User) session.getAttribute("user");
        if(user != null){
            // if personal token is change then update
            if(user.getPersonalTokenGitlab() == null || user.getPersonalTokenGitlab().equals(personalToken)){
                user.setPersonalTokenGitlab(personalToken);
                session.setAttribute("user", user);
                userService.saveUser(user);
            }
        }
        // save gitlab group id
        Class classA = classService.findById(classId);
        classA.setGitlabGroupId(groupIdOrPath);
        classService.saveClass(classA);
        return "redirect:/class/issue-setting?id=" + classId;
    }


}
