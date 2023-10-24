package swp.studentprojectportal.controller.subject_manager.sclass;

import jakarta.servlet.http.HttpSession;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.IssueSetting;
import swp.studentprojectportal.model.Milestone;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.IMilestoneService;
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.service.servicesimpl.GitlabApiService;
import swp.studentprojectportal.service.servicesimpl.SubjectService;
import swp.studentprojectportal.service.servicesimpl.UserService;
import swp.studentprojectportal.utils.dto.Mapper;

import java.util.List;

@Controller
public class MilestoneController {
    @Autowired
    IMilestoneService milestoneService;
    @Autowired
    ClassService classService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    GitlabApiService gitlabApiService;
    @Autowired
    UserService userService;


    @GetMapping("/class/milestone")
    public String milestonePage(@RequestParam("classId") Integer classId,@RequestParam(defaultValue = "0") Integer pageNo,
                                @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String search,
                                @RequestParam(defaultValue = "-1") Integer status, @RequestParam(defaultValue = "id") String sortBy,
                                @RequestParam(defaultValue = "1") Integer sortType, @RequestParam(defaultValue = "") Integer milestoneId,
                                Model model, HttpSession session) {
        Page<Milestone> milestoneList= milestoneService.filterMilestone(classId, search, pageNo, pageSize,sortBy, sortType, status);
        Class classA = classService.findById(classId);
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
        model.addAttribute("totalPage", milestoneList.getTotalPages());
        model.addAttribute("milestoneList", milestoneList);

        //milestone detail
        Milestone milestone = milestoneService.findMilestoneById(milestoneId);
        model.addAttribute("milestone", milestone);
        return "subject_manager/milestone/classMilestoneList";
    }

    @GetMapping("/class/milestone/updateStatus")
    public String updateSubjectSettingStatus(
            @RequestParam int id,
            @RequestParam boolean status) {
        Milestone milestone = milestoneService.findMilestoneById(id);
        milestone.setStatus(status);
        milestoneService.save(milestone);
        return "redirect:/";
    }


    @GetMapping("/milestone/sync-gitlab")
    public String synchronizeGitlabMilestoneClass(
            @RequestParam(name = "classId", defaultValue = "-1") Integer classId,
            @RequestParam(name = "group") String groupIdOrPath,
            @RequestParam(name = "personalToken") String personalToken,
            HttpSession session
    ) throws GitLabApiException {
        List<org.gitlab4j.api.models.Milestone> milestoneListGitlab = null;
        try {
            milestoneListGitlab =  gitlabApiService.getClassMilestoneGitlab(groupIdOrPath, personalToken);
        } catch (GitLabApiException e) {
            System.out.printf(e.getMessage());
        }
        if(milestoneListGitlab == null) {
            return "redirect:/class/milestone?classId=" + classId;
        }
        List<swp.studentprojectportal.model.Milestone> milestoneListDB = milestoneService.findMilestoneByClassId(classId);

        // sync to db
        for (org.gitlab4j.api.models.Milestone milestone : milestoneListGitlab) {
            boolean isExist = false;
            boolean isUpdate = false;
            for (swp.studentprojectportal.model.Milestone milestoneDB : milestoneListDB) {
                if (Mapper.milestoneEquals(milestoneDB, milestone)) {
                    isExist = true;
                    if(milestoneDB.getUpdateAt().before(milestone.getUpdatedAt())){
                        isUpdate = true;
                    }
                    break;
                }
            }

            try {
                if (!isExist) {
                    swp.studentprojectportal.model.Milestone milestoneDB = Mapper.milestoneConvert(milestone);
                    milestoneDB.setAclass(classService.findById(classId));
                    milestoneService.save(milestoneDB);
                } else {
                    if(isUpdate){
                        swp.studentprojectportal.model.Milestone milestoneDB = Mapper.milestoneConvert(milestone);
                        milestoneDB.setId(milestoneService.findMilestoneByTitle(milestone.getTitle()).getId());
                        milestoneDB.setAclass(classService.findById(classId));
                        milestoneService.save(milestoneDB);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        // sync to gitlab
        for (swp.studentprojectportal.model.Milestone milestoneDB : milestoneListDB) {
            boolean isExist = false;
            boolean isUpdate = false;
            for (org.gitlab4j.api.models.Milestone milestone : milestoneListGitlab) {
                if (Mapper.milestoneEquals(milestoneDB, milestone)) {
                    isExist = true;
                    if(milestoneDB.getUpdateAt().after(milestone.getUpdatedAt())){
                        isUpdate = true;
                    }
                    break;
                }
            }

            if (!isExist) {
                org.gitlab4j.api.models.Milestone milestoneGitlab = Mapper.milestoneConvert(milestoneDB);
                gitlabApiService.createGrouptMilestone(groupIdOrPath, personalToken, milestoneGitlab);
            } else {
                if(isUpdate){
                    org.gitlab4j.api.models.Milestone milestoneGitlab = Mapper.milestoneConvert(milestoneDB);
//                    gitlabApiService.updateClassMilestone(groupIdOrPath, personalToken, milestoneGitlab);
                }
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
        return "redirect:/class/milestone?classId=" + classId;
    }
}
