package swp.studentprojectportal.controller.project_mentor;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Submission;
import swp.studentprojectportal.model.SubmitIssue;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.SubmissionService;
import swp.studentprojectportal.service.servicesimpl.SubmitIssueService;

@Controller
@RequestMapping("/project-mentor/submission")
public class SubmissionProjectMentorController {
    @Autowired
    SubmissionService submissionService;
    @Autowired
    SubmitIssueService submitIssueService;

    @GetMapping("/details/{submissionId}")
    public String submissionDetails(HttpSession session, Model model, @PathVariable Integer submissionId) {
        User user = (User) session.getAttribute("user");

        model.addAttribute("isMentor", user.getSetting().getId() == 4);
        Submission submission = submissionService.findById(submissionId);
        Integer subjectId = -1 ;
        if(submission.getMilestone().getAclass()!=null){
            subjectId = submission.getMilestone().getAclass().getSubject().getId();
        }
        else if(submission.getMilestone().getProject()!=null){
            subjectId = submission.getMilestone().getProject().getAclass().getSubject().getId();
        }
        model.addAttribute("subjectId",subjectId);
        model.addAttribute("submitIssueList", submitIssueService.findAllBySubmissionId(submissionId));
        model.addAttribute("submission", submissionService.findById(submissionId));
        model.addAttribute("listSubjectSettingComplexity",submitIssueService.findAllComplexitySubjectSettingValueById(subjectId));
        model.addAttribute("listSubjectSettingQuality",submitIssueService.findAllQualitySubjectSettingValueById(subjectId));

        return "project_mentor/submission/submissionDetails";
    }

    @GetMapping(path = "/submit-issue/reject")
    public String deleteSubmitIssue(@RequestParam int id,@RequestParam int submissionId, Model model){
        SubmitIssue submitIssue = submitIssueService.findById(id);
        submitIssue.setFunctionLoc(null);
        submitIssue.setComplexity(null);
        submitIssue.setQuality(null);
        submitIssue.setIsRejected(true);
        submitIssueService.saveSubmitIssue(submitIssue);
        return "redirect:/project-mentor/submission/details/" + submissionId;
    }

}
