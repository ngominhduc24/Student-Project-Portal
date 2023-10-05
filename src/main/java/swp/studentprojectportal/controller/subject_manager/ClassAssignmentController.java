package swp.studentprojectportal.controller.subject_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import swp.studentprojectportal.repository.IAssignmentRepository;
import swp.studentprojectportal.repository.IClassAssignmentRepository;
import swp.studentprojectportal.service.IAssignmentService;
import swp.studentprojectportal.service.IClassAssignmentService;

@Controller
public class ClassAssignmentController {
    @Autowired
    IClassAssignmentRepository classAssignmentRepository;
    @Autowired
    IClassAssignmentService classAssignmentService;

    @GetMapping("/assignment")
    public String AssignmentPage(Model model) {
        return "subject_manager/class_assignment/classAssignmentList";
    }

    @GetMapping("/addAssignment")
    public String AddSubjectAssignment(Model model){
        return "subject_manager/class_assignment/classAssignmentAdd";
    }

    @GetMapping("/assignmentDetail")
    public String subjectAssignmentDetail(Model model){
        return "subject_manager/class_assignment/classAssignmentDetails";
    }
}
