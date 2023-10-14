package swp.studentprojectportal.controller.subject_manager.sclass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import swp.studentprojectportal.service.IMilestoneService;

@Controller
public class MilestoneController {
    @Autowired
    IMilestoneService milestoneService;


    @GetMapping("/milestone/add")
    public String AddSubjectAssignment(Model model){
        return "subject_manager/milestone/milestoneAdd";
    }

    @GetMapping("/milestoneDetail")
    public String subjectAssignmentDetail(Model model){
        return "subject_manager/milestone/milestoneDetails";
    }
}
