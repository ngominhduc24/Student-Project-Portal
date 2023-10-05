package swp.studentprojectportal.controller.subject_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import swp.studentprojectportal.model.Assignment;
import swp.studentprojectportal.repository.IAssignmentRepository;
import swp.studentprojectportal.service.IAssignmentService;

@Controller
public class SubjectAssignmentController {
    @Autowired
    IAssignmentRepository assignmentRepository;
    @Autowired
    IAssignmentService assignmentService;

    @GetMapping("/subjectAssignment")
    public String AssignmentPage(Model model) {
        return "subject_manager/subject_assignment/subjectAssignmentList";
    }

    @GetMapping("/addSubjectAssignment")
    public String AddSubjectAssignment(Model model){
        return "subject_manager/subject_assignment/subjectAssignmentAdd";
    }

    @GetMapping("/subjectAssignmentDetail")
    public String subjectAssignmentDetail(Model model){
        return "subject_manager/subject_assignment/subjectAssignmentDetails";
    }
}
