package swp.studentprojectportal.controller.subject_manager.subject;

import com.sun.mail.imap.protocol.INTERNALDATE;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.Assignment;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.IAssignmentService;
import swp.studentprojectportal.service.ISubjectService;
import swp.studentprojectportal.utils.Validate;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class SubjectAssignmentController {
    @Autowired
    ISubjectService subjectService;
    @Autowired
    IAssignmentService assignmentService;

    @GetMapping("/subject-manager/subject-assignment/updateStatus")
    public String updateAssignmentStatus(
            @RequestParam int id,
            @RequestParam boolean status) {
        Assignment assignment = assignmentService.getAssignmentById(id);
        assignment.setStatus(status);
        assignmentService.saveAssignment(assignment);
        return "redirect:/";
    }


    @GetMapping("/subject-manager/addSubjectAssignment")
    public String AddSubjectAssignment(Model model, @RequestParam("subjectId") Integer subjectId){
        Subject subject = subjectService.getSubjectById(subjectId);
        model.addAttribute("assignment", new Assignment());
        model.addAttribute("subject", subject);
        return "subject_manager/subject_assignment/subjectAssignmentAdd";
    }

    @PostMapping("/subject-manager/addAssignment")
    public String createAssignment(Model model, WebRequest request){
        int subjectId = Integer.parseInt(request.getParameter("subject"));
        String title = Objects.requireNonNull(request.getParameter("title")).trim();
        String description = Objects.requireNonNull(request.getParameter("description")).trim();
        String errorMessage = checkValidateAssignment(title, description);
        if(errorMessage != null) {
            model.addAttribute("errorMsg", errorMessage);
            model.addAttribute("title", title);
            model.addAttribute("description", description);
            model.addAttribute("subject", subjectService.getSubjectById(subjectId));
            return "subject_manager/subject_assignment/subjectAssignmentAdd";
        }

        assignmentService.addAssignment(title, description, subjectId, true);
        return "redirect:/subject-manager/subject?subjectId=" + subjectId;
    }

    @GetMapping("/subject-manager/subjectAssignmentDetail")
    public String subjectAssignmentDetail(Model model, @RequestParam("id") Integer id, @RequestParam("subjectId") Integer subjectId){
        Assignment assignment = assignmentService.getAssignmentById(id);
        Subject subject = subjectService.getSubjectById(subjectId);
        model.addAttribute("assignment", assignment);
        model.addAttribute("subject", subject);
        return "subject_manager/subject_assignment/subjectAssignmentDetails";
    }

    @PostMapping("/subject-manager/updateAssignment")
    public String updateAssignment(Model model, WebRequest request){

        int id = Integer.parseInt(Objects.requireNonNull(request.getParameter("id")));
        String title = Objects.requireNonNull(request.getParameter("title")).trim();
        String description = Objects.requireNonNull(request.getParameter("description")).trim();
        int subjectId = Integer.parseInt(Objects.requireNonNull(request.getParameter("subject")));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));

        String errorMessage = checkValidateAssignment(title, description);
        if (errorMessage != null) {
            model.addAttribute("errorMsg", errorMessage);
            model.addAttribute("subject", subjectService.getSubjectById(subjectId));
        } else {
            boolean ans = assignmentService.updateAssignment(id, title, description, subjectId, status);
            if (ans) model.addAttribute("successMsg", "Update success");
            else model.addAttribute("errorMsg", "Update failed");
        }

        model.addAttribute("assignment", assignmentService.getAssignmentById(id));

        return "redirect:/subject-manager/subjectAssignmentDetail?id=" + id +"&subjectId=" + subjectId ;
    }

    private String checkValidateAssignment(String title, String description) {
        if(title.isEmpty()) return "Please input subject assignment title";
        if(description.isEmpty()) return "Please input subject assignment description";
        if(!Validate.validTilteDescription(title, description)) return "Please dont input special characters";
        return null;
    }

}
