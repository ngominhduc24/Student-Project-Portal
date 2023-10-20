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

    @GetMapping("/milestone/add")
    public String AddClassAssignment(Model model){
        return "subject_manager/milestone/milestoneAdd";
    }

    @GetMapping("/subject-manager/milestoneDetail")
    public String classAssignmentDetail(Model model, @RequestParam("id") Integer milestoneId){
        Milestone milestone = milestoneService.findMilestoneById(milestoneId);
        model.addAttribute("milestone", milestone);
        return "subject_manager/milestone/milestoneDetails";
    }

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


    @GetMapping("/milestone/sync-gitlab")
    public String synchronizeGitlabMilestoneClass(
            @RequestParam(name = "classId", defaultValue = "-1") Integer classId,
            @RequestParam(name = "group") String groupIdOrPath,
            @RequestParam(name = "personalToken") String personalToken
    ) throws GitLabApiException {
        List<org.gitlab4j.api.models.Milestone> milestoneListGitlab =  gitlabApiService.getClassMilestoneGitlab(groupIdOrPath, personalToken);
        List<swp.studentprojectportal.model.Milestone> milestoneListDB = milestoneService.findMilestoneByClassId(classId);

        // sync to db
        for (org.gitlab4j.api.models.Milestone milestone : milestoneListGitlab) {
            boolean isExist = false;
            for (swp.studentprojectportal.model.Milestone milestoneDB : milestoneListDB) {
                if (Mapper.milestoneEquals(milestoneDB, milestone)) {
                    isExist = true;
                    break;
                }
            }

            if (!isExist) {
                swp.studentprojectportal.model.Milestone milestoneDB = Mapper.milestoneConvert(milestone);
                milestoneDB.setAclass(classService.findById(classId));
                milestoneService.save(milestoneDB);
            }
        }

        // sync to gitlab
        for (swp.studentprojectportal.model.Milestone milestoneDB : milestoneListDB) {
            boolean isExist = false;
            for (org.gitlab4j.api.models.Milestone milestone : milestoneListGitlab) {
                if (Mapper.milestoneEquals(milestoneDB, milestone)) {
                    isExist = true;
                    break;
                }
            }

            if (!isExist) {
                org.gitlab4j.api.models.Milestone milestoneGitlab = Mapper.milestoneConvert(milestoneDB);
                gitlabApiService.createGrouptMilestone(groupIdOrPath, personalToken, milestoneGitlab);
            }
        }
        return "redirect:/class/milestone?classId=" + classId;
    }
}
