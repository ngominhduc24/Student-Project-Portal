package swp.studentprojectportal.controller.subject_manager.sclass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Milestone;
import swp.studentprojectportal.service.IMilestoneService;

@Controller
public class MilestoneController {
    @Autowired
    IMilestoneService milestoneService;


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
}
