package swp.studentprojectportal.controller.subject_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import swp.studentprojectportal.model.Assignment;
import swp.studentprojectportal.repository.IAssignmentRepository;
import swp.studentprojectportal.service.IAssignmentService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class SubjectAssignmentController {

    @Autowired
    IAssignmentService assignmentService;

    List<Assignment> assignmentList = new CopyOnWriteArrayList<>();
    @GetMapping("subject-manager/subject-assignment")
    public String AssignmentPage(Model model) {
        assignmentList = assignmentService.findAllAssignment(0,10);
        model.addAttribute("assignmentList", assignmentList);
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
