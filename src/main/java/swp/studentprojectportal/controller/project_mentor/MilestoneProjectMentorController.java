package swp.studentprojectportal.controller.project_mentor;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.Milestone;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.MilestoneService;
import swp.studentprojectportal.service.servicesimpl.ProjectService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/project-mentor")
public class MilestoneProjectMentorController {
    @Autowired
    MilestoneService milestoneService;
    @Autowired
    ProjectService projectService;

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

        model.addAttribute("projectId", projectId);
        model.addAttribute("projectList", projectService.findAllByProjectMentorId(user.getId()));
        model.addAttribute("isMentor", user.getSetting().getId() == 4);
        model.addAttribute("milestoneList", milestoneService.findAllByProjectId(projectId));
        return "project_mentor/milestone/milestoneList";

    }

}
