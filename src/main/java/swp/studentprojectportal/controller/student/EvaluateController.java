package swp.studentprojectportal.controller.student;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import swp.studentprojectportal.model.*;
import swp.studentprojectportal.service.servicesimpl.*;
import swp.studentprojectportal.utils.dto.Mapper;

import java.util.List;

@Controller
@RequestMapping("/student")
public class EvaluateController {

    @Autowired
    EvaluationService evaluationService;

    @Autowired
    SubmitIssueService submitIssueService;

    @Autowired
    SubmissionService submissionService;

    @Autowired
    SubmissionPersonalService submissionPersonalService;

    @Autowired
    ProjectService projectService;

    @Autowired
    MilestoneService milestoneService;
    @GetMapping(value = "/submission/evaluate/{submissionId}")
    public String evaluationList(HttpSession session, Model model,
                                 @PathVariable Integer submissionId
    ) {
        List<Evaluation> evaluation =  evaluationService.getEvaluationBySubmissionId(submissionId);

        if(evaluation == null || evaluation.size() == 0) {
            evaluation = evaluationService.createEvaluation(submissionId);
        }

        // Map evaluation to evaluationDTO
        List<EvaluationDTO> evaluationDTO = Mapper.evaluationMapper(evaluation);

        // Set work point and work grade
        evaluationDTO.forEach(item -> {
            EvaluationDTO temp = submitIssueService.setWorkPoint(item);
            item.setWorkPoint(temp.getWorkPoint());
            item.setWorkGrade(temp.getWorkGrade());
        });

        // Set bonus and comment personal
        List<SubmissionPersonal> submissionPersonalList = submissionPersonalService.getAllSubmissionPersonalBySubmissionId(submissionId);
        evaluationDTO.forEach(item -> {
            submissionPersonalList.forEach(e -> {
                if(e.getStudent().getId() == item.getStudentId()) {
                    item.setBonusAndPenalty(e.getBonus());
                    item.setCommentPersonal(e.getComment());
                }
            });
        });

        // set atriibute
        model.addAttribute("submissionId", submissionId);
        model.addAttribute("evaluationDTO", evaluationDTO);
        model.addAttribute("submission", evaluation.get(0).getSubmission());

        // Get project list and milestone list of mentor
        User user = (User) session.getAttribute("user");
//        List<Submission> submissionList = submissionService.findAllByProjectMentorId(user.getId());

        List<Project> projectList = projectService.findAllByProjectMentorId(user.getId());
        model.addAttribute("projectList", projectList);

        List<Milestone> milestoneList = milestoneService.findAllByProjectMentor(user.getId());
        model.addAttribute("milestoneList", milestoneList);

        model.addAttribute("classList",projectList.stream().map(Project::getAclass).distinct().toList());

        model.addAttribute("isStudent", true);
        return "project_mentor/submission/submissionEvaluations";
    }
}
