package swp.studentprojectportal.controller.project_mentor;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Evaluation;
import swp.studentprojectportal.model.EvaluationDTO;
import swp.studentprojectportal.service.servicesimpl.EvaluationService;
import swp.studentprojectportal.service.servicesimpl.SubmissionService;
import swp.studentprojectportal.utils.dto.Mapper;

import java.util.List;

@Controller
@RequestMapping("/project-mentor")
public class AssignmentEvaluationsController {
    @Autowired
    EvaluationService evaluationService;

    @GetMapping("/evaluation")
    public String evaluationList(HttpSession session, Model model,
                                @RequestParam(defaultValue = "-1") Integer submissionId
    ) {
        List<Evaluation> evaluation =  evaluationService.getEvaluationBySubmissionId(submissionId);
        // Map evaluation to evaluationDTO
        List<EvaluationDTO> evaluationDTO = Mapper.evaluationMapper(evaluation);
        System.out.println(evaluationDTO.size());
        evaluationDTO.forEach(e -> {
            System.out.println(e.getFullname());
            e.getCriteriaGradeList().forEach(c -> {
                System.out.println(c.getCriteriaName() + " " + c.getGrade());
            });
        });
        // set atriibute
        model.addAttribute("evaluationDTO", evaluationDTO);
//        model.addAttribute("evaluation", evaluation);
        return "project_mentor/submission/submissionEvaluations";
    }
}
