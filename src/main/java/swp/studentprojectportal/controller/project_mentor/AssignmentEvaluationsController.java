package swp.studentprojectportal.controller.project_mentor;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import swp.studentprojectportal.model.Evaluation;
import swp.studentprojectportal.model.EvaluationDTO;
import swp.studentprojectportal.model.SubmitIssue;
import swp.studentprojectportal.service.servicesimpl.EvaluationService;
import swp.studentprojectportal.service.servicesimpl.SubmissionService;
import swp.studentprojectportal.service.servicesimpl.SubmitIssueService;
import swp.studentprojectportal.utils.dto.Mapper;

import java.net.http.HttpRequest;
import java.util.List;

@Controller
@RequestMapping("/project-mentor")
public class AssignmentEvaluationsController {
    @Autowired
    EvaluationService evaluationService;

    @Autowired
    SubmitIssueService submitIssueService;

    @GetMapping("/evaluation")
    public String evaluationList(HttpSession session, Model model,
                                @RequestParam(defaultValue = "-1") Integer submissionId
    ) {
        List<Evaluation> evaluation =  evaluationService.getEvaluationBySubmissionId(submissionId);

        if(evaluation == null || evaluation.size() == 0) {
            evaluation = evaluationService.createEvaluation(submissionId);
        }

        // Map evaluation to evaluationDTO
        List<EvaluationDTO> evaluationDTO = Mapper.evaluationMapper(evaluation);

        evaluationDTO.forEach(item -> {
            EvaluationDTO temp = submitIssueService.setWorkPoint(item);
            item.setWorkPoint(temp.getWorkPoint());
            item.setWorkGrade(temp.getWorkGrade());
        });

        // set atriibute
        model.addAttribute("submissionId", submissionId);
        model.addAttribute("evaluationDTO", evaluationDTO);
        model.addAttribute("submission", evaluation.get(0).getSubmission());
        return "project_mentor/submission/submissionEvaluations";
    }

    @PostMapping("/evaluation")
    public String evaluationUpdate(HttpSession session, Model model, WebRequest request, RedirectAttributes attributes) {
        String submissionId = request.getParameter("submissionId");
        String[] evalGrades = request.getParameterValues("evalGrade");
        String[] evalGradeId = request.getParameterValues("evalGradeId");
        Float submissionMark = 0f;
        for (int i = 0; i < evalGradeId.length; i++) {
            System.out.println(evalGradeId[i] + " " + evalGrades[i]);
            try {
                evaluationService.updateEvaluation(Integer.parseInt(evalGradeId[i]), Float.parseFloat(evalGrades[i]));
            } catch (Exception e) {
                attributes.addFlashAttribute("emessage", "Update failed");
                return "redirect:/project-mentor/evaluation?submissionId=" + submissionId;
            }
        }
        attributes.addFlashAttribute("smessage", "Update successfully");
        return "redirect:/project-mentor/evaluation?submissionId=" + submissionId;
    }
}
