package swp.studentprojectportal.controller.project_mentor;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import swp.studentprojectportal.model.*;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.service.servicesimpl.MilestoneService;
import swp.studentprojectportal.service.servicesimpl.ProjectService;
import swp.studentprojectportal.service.servicesimpl.SubmissionService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/project-mentor")
public class MilestoneProjectMentorController {
    @Autowired
    MilestoneService milestoneService;
    @Autowired
    ProjectService projectService;
    @Autowired
    SubmissionService submissionService;

    @GetMapping("/milestone/list")
    public String milestoneList(HttpSession session) {

        User user = (User) session.getAttribute("user");
        List<Project> projectList = projectService.findAllByProjectMentorId(user.getId());

        if(projectList.isEmpty()) return "redirect:./list/";

        return "redirect:./list/" + projectList.get(0).getId();

    }

    @GetMapping("/milestone/list/")
    public String milestoneList(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");

        model.addAttribute("projectList", projectService.findAllByProjectMentorId(user.getId()));
        model.addAttribute("isMentor", user.getSetting().getId() == 4);
        model.addAttribute("milestoneList", milestoneService.findAllByProjectMentor(user.getId()));
        return "project_mentor/milestone/milestoneList";

    }

    @GetMapping("/milestone/list/{projectId}")
    public String milestoneList(HttpSession session, Model model, @PathVariable Integer projectId) {

        User user = (User) session.getAttribute("user");

        Project project = projectService.findById(projectId);

        List<Milestone> milestoneList = milestoneService.findAllBySubjectAndClassOfProject(project.getAclass().getId());

        //filter submission of milestone
        for (Milestone milestone : milestoneList)
            if(milestone.getSubmissionList() != null)
                milestone.getSubmissionList().removeIf(submission -> submission.getProject().getId() != projectId);

        model.addAttribute("project", project);
        model.addAttribute("projectList", projectService.findAllByProjectMentorId(user.getId()));
        model.addAttribute("isMentor", user.getSetting().getId() == 4);
        model.addAttribute("milestoneList", milestoneList);
        return "project_mentor/milestone/milestoneList";

    }

}
