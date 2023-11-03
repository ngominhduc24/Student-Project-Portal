package swp.studentprojectportal.controller.project_mentor;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.Milestone;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.MilestoneService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/project-mentor")
public class MilestoneProjectMentorController {
    @Autowired
    MilestoneService milestoneService;

    @GetMapping("/milestone/list")
    public String milestoneList(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");

        List<Milestone> milestoneList = new ArrayList<>();

        //student
        if(user.getSetting().getId() == 1) {
            model.addAttribute("milestoneList", milestoneService.findAllByStudentId(user.getId()));
        }

        //project mentor
        if(user.getSetting().getId() == 4) {
            milestoneList.addAll(milestoneService.findAllByProjectMentor(user.getId()));
        }

        model.addAttribute("isMentor", user.getSetting().getId() == 4);
        model.addAttribute("milestoneList", milestoneList);
        return "project_mentor/milestone/milestoneList";

    }

}
