package swp.studentprojectportal.controller.subject_manager;

import jakarta.servlet.http.HttpSession;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import swp.studentprojectportal.model.*;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.service.IMilestoneService;
import swp.studentprojectportal.service.servicesimpl.*;
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
    @Autowired
    ProjectService projectService;
    @Autowired
    AssignmentService assignmentService;


    @GetMapping("/class/milestone")
    public String milestonePage(@RequestParam("classId") Integer classId, @RequestParam(defaultValue = "0") Integer pageNo,
                                @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String search,
                                @RequestParam(defaultValue = "-1") Integer status, @RequestParam(defaultValue = "id") String sortBy,
                                @RequestParam(defaultValue = "1") Integer sortType, @RequestParam(defaultValue = "") Integer milestoneId,
                                Model model, HttpSession session) {
        Page<Milestone> milestoneList = milestoneService.filterMilestone(classId, search, pageNo, pageSize, sortBy, sortType, status);
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
        return "subject_manager/class/classMilestoneList";
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
            HttpSession session, RedirectAttributes attributes
    ) throws GitLabApiException {
        List<org.gitlab4j.api.models.Milestone> milestoneListGitlab = null;
        List<swp.studentprojectportal.model.Milestone> milestoneListDB = milestoneService.findMilestoneByClassId(classId);
        List<Assignment> milestoneSubjectListDB = assignmentService.getAssignmentBySubjectId(classService.findById(classId).getSubject().getId());

        try {
            milestoneListGitlab = gitlabApiService.getClassMilestoneGitlab(groupIdOrPath, personalToken);
        } catch (GitLabApiException e) {
            System.out.printf(e.getMessage());
        }
        if (milestoneListGitlab == null) {
            attributes.addFlashAttribute("emessage", "GroupId or Token gitlab not valid!");
            return "redirect:/class/milestone?classId=" + classId;
        }

        // sync to db
        for (org.gitlab4j.api.models.Milestone milestone : milestoneListGitlab) {
            boolean isExist = false;
            for (swp.studentprojectportal.model.Milestone milestoneDB : milestoneListDB) {
                if (Mapper.milestoneEquals(milestoneDB, milestone)) {
                    isExist = true;
                    if(milestoneDB.getGitlabMilestoneId() != null) {
                        milestoneDB.setGitlabMilestoneId(milestone.getId());
                        milestoneService.save(milestoneDB);
                    }
                }

                if(milestoneDB.getGitlabMilestoneId() == null) {
                    continue;
                }

                // if milestone in db is updated then update


                if (milestone.getId().longValue() == milestoneDB.getGitlabMilestoneId().longValue()) {
                    isExist = true;
                    if (milestoneDB.getUpdateAt().before(milestone.getUpdatedAt())) {
                        swp.studentprojectportal.model.Milestone milestoneUpdated = Mapper.milestoneConvert(milestone);
                        milestoneUpdated.setId(milestoneDB.getId());
                        milestoneUpdated.setAclass(classService.findById(classId));
                        milestoneService.save(milestoneUpdated);
                    }
                    break;
                }

            }

            try {
                if (!isExist) {
                    swp.studentprojectportal.model.Milestone milestoneDB = Mapper.milestoneConvert(milestone);
                    milestoneDB.setGitlabMilestoneId(milestone.getId());
                    milestoneDB.setAclass(classService.findById(classId));
                    milestoneService.save(milestoneDB);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        // sync to gitlab
        for (swp.studentprojectportal.model.Milestone milestoneDB : milestoneListDB) {
            boolean isExist = false;
            for (org.gitlab4j.api.models.Milestone milestone : milestoneListGitlab) {
                // Check exist title to create new milestone
                if (Mapper.milestoneEquals(milestoneDB, milestone)) {
                    isExist = true;
                }

                if(milestoneDB.getGitlabMilestoneId() == null) {
                    continue;
                }

                // if exist milestone are sync then update
                if (milestone.getId().longValue() == milestoneDB.getGitlabMilestoneId().longValue()) {
                    isExist = true;
                    if (milestoneDB.getUpdateAt().after(milestone.getUpdatedAt())) {
                        org.gitlab4j.api.models.Milestone milestoneGitlab = Mapper.milestoneConvert(milestoneDB);
                        milestoneGitlab.setId(milestoneDB.getGitlabMilestoneId());
                        System.out.println("title: " + milestoneGitlab.getTitle() + "status " + milestoneDB.isStatus());
                        gitlabApiService.updateClassMilestone(groupIdOrPath, personalToken, milestoneGitlab, milestoneDB.isStatus());
                    }
                    break;
                }
            }

            if (!isExist) {
                // create milestone in gitlab
                org.gitlab4j.api.models.Milestone milestoneGitlab = gitlabApiService.createGrouptMilestone(groupIdOrPath, personalToken, Mapper.milestoneConvert(milestoneDB));
                // update milestone id in db
                milestoneDB.setGitlabMilestoneId(milestoneGitlab.getId());
                milestoneService.save(milestoneDB);
            }
        }

        // save personal token
        User user = (User) session.getAttribute("user");
        if (user != null) {
            // if personal token is change then update
            if (user.getPersonalTokenGitlab() == null || user.getPersonalTokenGitlab().equals(personalToken)) {
                user.setPersonalTokenGitlab(personalToken);
                session.setAttribute("user", user);
                userService.saveUser(user);
            }
        }

        // save gitlab group id
        Class classA = classService.findById(classId);
        classA.setGitlabGroupId(groupIdOrPath);
        classService.saveClass(classA);
        attributes.addFlashAttribute("smessage", "synchronized with Gitlab successful!");
        return "redirect:/class/milestone?classId=" + classId;
    }

    @GetMapping("/class-manager/project/milestone")
    public String projectMilestonePage(@RequestParam("projectId") Integer projectId, @RequestParam(defaultValue = "0") Integer pageNo,
                                       @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String search,
                                       @RequestParam(defaultValue = "-1") Integer status, @RequestParam(defaultValue = "id") String sortBy,
                                       @RequestParam(defaultValue = "1") Integer sortType, @RequestParam(defaultValue = "") Integer milestoneId,
                                       Model model, HttpSession session) {
        Project project = projectService.findById(projectId);
        Page<Milestone> milestoneList = milestoneService.filterMilestoneByProject(project.getAclass().getId(), projectId, search, pageNo, pageSize, sortBy, sortType, status);
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
        model.addAttribute("totalPage", milestoneList.getTotalPages());
        model.addAttribute("milestoneList", milestoneList);

        //milestone detail
        Milestone milestone = milestoneService.findMilestoneById(milestoneId);
        model.addAttribute("milestone", milestone);
        return "class_manager/project/projectMilestoneList";
    }
}
