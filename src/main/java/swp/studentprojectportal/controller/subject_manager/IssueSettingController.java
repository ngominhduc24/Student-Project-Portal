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
import swp.studentprojectportal.model.*;
import swp.studentprojectportal.model.Class;
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
    @Autowired
    ProjectService projectService;

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
        model.addAttribute("groupGitlabId", classA.getGitlabGroupId());
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

        return "subject_manager/class/issueSettingList";
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

        return "subject_manager/class/issueSettingList";
    }


    @GetMapping(value={"/class/issue-setting/updateStatus", "/class-manager/project/issue-setting/updateStatus"})
    public String updateSubjectSettingStatus(
            @RequestParam int id,
            @RequestParam boolean status) {
        IssueSetting issueSetting = issueSettingService.findById(id);
        issueSetting.setStatus(status);
        issueSettingService.saveSubjectSetting(issueSetting);
        return "redirect:/";
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

    @GetMapping("/class-manager/project/issue-setting")
    public String projectIssueSettingPage(@RequestParam("id") Integer projectId,@RequestParam(defaultValue = "0") Integer pageNo,
                                   @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String search,
                                   @RequestParam(defaultValue = "-1") Integer status, @RequestParam(defaultValue = "id") String sortBy,
                                   @RequestParam(defaultValue = "1") Integer sortType, @RequestParam(defaultValue = "") String settingGroup,
                                   Model model, HttpSession session) {
        Project project = projectService.findById(projectId);
        Page<IssueSetting> issueSettingList= issueSettingService.filterProjectIssueSetting(project.getAclass().getSubject().getId(), project.getAclass().getId(), projectId, search, pageNo, pageSize, sortBy, sortType, settingGroup, status);
        List<String> settingGroupList = issueSettingService.findAllDistinctProjectSettingGroup(project.getAclass().getSubject().getId(), projectId, project.getAclass().getId());
        IssueSetting setting = new IssueSetting();
        setting.setProject(project);
        setting.setSettingGroup("Not Empty");
        setting.setSettingTitle("");
        setting.setDescription("");
        model.addAttribute("setting",setting);

        User user = (User) session.getAttribute("user");
        model.addAttribute("subjectList", subjectService.findAllSubjectByUserAndStatus(user, true));
        model.addAttribute("personalToken", user.getPersonalTokenGitlab());
//        model.addAttribute("groupGitlabId", classA.getGitlabGroupId());
        model.addAttribute("project", project);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("projectId", projectId);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("status", status);
        model.addAttribute("totalPage", issueSettingList.getTotalPages());
        model.addAttribute("settingGroup", settingGroup);

        model.addAttribute("issueSettingList", issueSettingList);
        model.addAttribute("settingGroupList", settingGroupList);

        return "class_manager/project/projectIssueSettingList";
    }

    @PostMapping("/class-manager/project/issue-setting")
    public String projectIssueSettingAdd(@RequestParam("id") Integer projectId,@RequestParam(defaultValue = "0") Integer pageNo,
                                  @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String search,
                                  @RequestParam(defaultValue = "-1") Integer status, @RequestParam(defaultValue = "id") String sortBy,
                                  @RequestParam(defaultValue = "1") Integer sortType, @RequestParam(defaultValue = "") String settingGroup,
                                  @RequestParam String description,
                                  @RequestParam("newSettingGroup") String newSettingGroup,
                                  @RequestParam("newSettingTitle") String newSettingTitle,
                                  Model model, HttpSession session) {
        Project project = projectService.findById(projectId);
        Page<IssueSetting> issueSettingList= issueSettingService.filterProjectIssueSetting(project.getAclass().getSubject().getId(), project.getAclass().getId(), projectId, search, pageNo, pageSize, sortBy, sortType, settingGroup, status);
        List<String> settingGroupList = issueSettingService.findAllDistinctProjectSettingGroup(project.getAclass().getSubject().getId(), projectId, project.getAclass().getId());

        IssueSetting setting = new IssueSetting();
        setting.setProject(project);
        setting.setSettingGroup(newSettingGroup);
        setting.setSettingTitle(newSettingTitle);
        setting.setDescription(description);
        if(newSettingGroup.equals("Not Empty") == false) {
            if (Validate.validNotempty(newSettingGroup) == false) {
                String errmsg = "Group can't empty. Add failed!";
                model.addAttribute("errmsg", errmsg);
            } else {
                if (issueSettingService.findByClassAndGroupAndTitle(project.getAclass().getId(), newSettingGroup, newSettingTitle) != null) {
                    String errmsg = "Issue setting existed. Add failed!";
                    model.addAttribute("errmsg", errmsg);
                } else {
                    issueSettingService.saveSubjectSetting(setting);
                    setting = new IssueSetting();
                    setting.setProject(project);
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
        model.addAttribute("project", project);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("projectId", projectId);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("status", status);
        model.addAttribute("totalPage", issueSettingList.getTotalPages());
        model.addAttribute("settingGroup", settingGroup);

        model.addAttribute("issueSettingList", issueSettingList);
        model.addAttribute("settingGroupList", settingGroupList);

        return "class_manager/project/projectIssueSettingList";
    }


}
