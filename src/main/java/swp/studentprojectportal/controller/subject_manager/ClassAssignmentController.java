package swp.studentprojectportal.controller.subject_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import swp.studentprojectportal.model.Milestone;
import swp.studentprojectportal.service.IMilestoneService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class ClassAssignmentController {
    @Autowired
    IMilestoneService classAssignmentService;


    @GetMapping("/addAssignment")
    public String AddSubjectAssignment(Model model){
        return "subject_manager/class_assignment/classAssignmentAdd";
    }

    @GetMapping("/assignmentDetail")
    public String subjectAssignmentDetail(Model model){
        return "subject_manager/class_assignment/classAssignmentDetails";
    }
}
