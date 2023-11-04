package swp.studentprojectportal.controller.student;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.SubmissionService;
import swp.studentprojectportal.service.servicesimpl.SubmitIssueService;

@Controller
@RequestMapping("/student/submission")
public class SubmissionStudentController {
    @Autowired
    SubmissionService submissionService;
    @Autowired
    SubmitIssueService submitIssueService;

    @GetMapping("/details/{submissionId}")
    public String submissionDetails(HttpSession session, Model model, @PathVariable Integer submissionId) {
        User user = (User) session.getAttribute("user");

        model.addAttribute("isMentor", user.getSetting().getId() == 4);
        model.addAttribute("submitIssueList", submitIssueService.findAllBySubmissionId(submissionId));
        model.addAttribute("submission", submissionService.findById(submissionId));

        return "project_mentor/submission/submissionDetails";
    }

}
