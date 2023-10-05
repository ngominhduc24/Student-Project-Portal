package swp.studentprojectportal.controller.subject_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import swp.studentprojectportal.model.ClassAssignment;
import swp.studentprojectportal.repository.IAssignmentRepository;
import swp.studentprojectportal.repository.IClassAssignmentRepository;
import swp.studentprojectportal.service.IAssignmentService;
import swp.studentprojectportal.service.IClassAssignmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class ClassAssignmentController {
    @Autowired
    IClassAssignmentService classAssignmentService;

    List<ClassAssignment> classAssignmentList = new CopyOnWriteArrayList<>();

    @GetMapping("subject-manager/class-assignment")
    public String AssignmentPage(Model model) {
        classAssignmentList = classAssignmentService.getClassAssignment(0, 10);
        model.addAttribute("classAssignmentList", classAssignmentList);
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
