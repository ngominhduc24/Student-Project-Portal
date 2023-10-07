package swp.studentprojectportal.controller.subject_manager;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Assignment;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.SubjectSetting;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.IAssignmentRepository;
import swp.studentprojectportal.service.IAssignmentService;
import swp.studentprojectportal.service.ISubjectService;
import swp.studentprojectportal.service.servicesimpl.SubjectSevice;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class SubjectAssignmentController {

    @Autowired
    ISubjectService subjectService;

    @Autowired
    IAssignmentService assignmentService;


    // List<Assignment> assignmentList = new CopyOnWriteArrayList<>();
    @GetMapping("subject-manager/subject-assignment")
    public String AssignmentPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        //List<Assignment> assignmentList = assignmentService.findAssignmentByManager(user.getId());
        List<Assignment> assignmentList = assignmentService.findAssignmentByManager(user.getId());
        model.addAttribute("assignmentList", assignmentList);
        return "subject_manager/subject_assignment/subjectAssignmentList";
    }

    @GetMapping("/subject-manager/subject-assignment/updateStatus")
    public String updateAssignmentStatus(
            @RequestParam int id,
            @RequestParam boolean status) {
        Assignment assignment = assignmentService.findById(id);
        assignment.setStatus(status);
        assignmentService.saveAssignment(assignment);
        return "redirect:/";
    }

    @RequestMapping(path = "/subject-manager/subject-assignment/add")
    public String addAssignmentPage(Model model){
        return "subject_manager/subject_assignment/subjectAssignmentAdd";
    }

    @RequestMapping("/subject-manager/subject-assignment/detail")
    public String detailSubjectSetting(@RequestParam("id") int id, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Subject> subjectList = subjectService.findAllSubjectByUser(user);
        Assignment assignment = assignmentService.findById(id);
        model.addAttribute("setting",assignment);
        model.addAttribute("subjectList",subjectList);
        return "subjectAssignmentDetails";
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
