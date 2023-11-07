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
import swp.studentprojectportal.model.*;
import swp.studentprojectportal.service.servicesimpl.*;
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

    @Autowired
    SubmissionService submissionService;

    @Autowired
    SubmissionPersonalService submissionPersonalService;

    @Autowired
    ProjectService projectService;

    @Autowired
    MilestoneService milestoneService;

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

        // Set work point and work grade
        evaluationDTO.forEach(item -> {
            EvaluationDTO temp = submitIssueService.setWorkPoint(item);
            item.setWorkPoint(temp.getWorkPoint());
            item.setWorkGrade(temp.getWorkGrade());
        });

        // Set bonus and comment personal
        List<SubmissionPersonal> submissionPersonalList = submissionPersonalService.getAllSubmissionPersonalBySubmissionId(1);
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
        //filter milestone list where   status = Submitted
        model.addAttribute("milestoneList", milestoneList);
        return "project_mentor/submission/submissionEvaluations";
    }

    @PostMapping("/evaluation")
    public String evaluationUpdate(HttpSession session, Model model, WebRequest request, RedirectAttributes attributes) {
        Float submissionMark = 0f;
        String commentGroup = request.getParameter("commentGroup");
        String submissionId = request.getParameter("submissionId");

        /*
            Update comment group and submission mark
         */
        String[] evalGrades = request.getParameterValues("evalGrade");
        String[] evalGradeId = request.getParameterValues("evalGradeId");

        // Update comment
        try {
            submissionService.updateComment(Integer.parseInt(submissionId), commentGroup);
        }catch (Exception e) {
            attributes.addFlashAttribute("emessage", "Update failed");
            return "redirect:/project-mentor/evaluation?submissionId=" + submissionId;
        }

        for (int i = 0; i < evalGradeId.length; i++) {
            try {
                evaluationService.updateEvaluation(Integer.parseInt(evalGradeId[i]), Float.parseFloat(evalGrades[i]));
            } catch (Exception e) {
                attributes.addFlashAttribute("emessage", "Update failed");
                return "redirect:/project-mentor/evaluation?submissionId=" + submissionId;
            }
        }

        /*
            Update comment personal and bonus
         */
        String[] commentPersonal = request.getParameterValues("evalCommentPersonal");
        String[] bonus = request.getParameterValues("evalBonusAndPenalty");
        String[] studentId = request.getParameterValues("evalStudentId");

        for (int i = 0; i < studentId.length; i++) {
            try {
                submissionPersonalService.updateSubmissionPersonal(Integer.parseInt(submissionId), Integer.parseInt(studentId[i]), Integer.parseInt(bonus[i]), commentPersonal[i]);
            } catch (Exception e) {
                attributes.addFlashAttribute("emessage", "Update failed");
                return "redirect:/project-mentor/evaluation?submissionId=" + submissionId;
            }
        }
        attributes.addFlashAttribute("smessage", "Update successfully");
        return "redirect:/project-mentor/evaluation?submissionId=" + submissionId;
    }
}
