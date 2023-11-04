package swp.studentprojectportal.controller.project_mentor;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Evaluation;
import swp.studentprojectportal.service.servicesimpl.EvaluationService;
import swp.studentprojectportal.service.servicesimpl.SubmissionService;

@Controller
@RequestMapping("/project-mentor")
public class AssignmentEvaluationsController {
    @Autowired
    EvaluationService evaluationService;

    @GetMapping("/evaluation")
    public String evaluationList(HttpSession session, Model model,
                                @RequestParam(defaultValue = "-1") Integer submissionId
    ) {
        Evaluation evaluation =  evaluationService.getEvaluationBySubmissionId(submissionId);
        // set atriibute
        model.addAttribute("evaluation", evaluation);
        return "project_mentor/submission/submissionEvaluations";
    }
}
