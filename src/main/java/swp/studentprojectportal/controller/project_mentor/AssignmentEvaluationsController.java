package swp.studentprojectportal.controller.project_mentor;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/project-mentor")
public class AssignmentEvaluationsController {

    @GetMapping("/evaluation")
    public String evaluationList(HttpSession session,
                                @RequestParam(defaultValue = "-1") String assignmentId
    ) {
        return "project_mentor/submission/submissionEvaluations";
    }
}
