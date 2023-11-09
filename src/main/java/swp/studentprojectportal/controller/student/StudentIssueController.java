package swp.studentprojectportal.controller.student;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Issue;
import swp.studentprojectportal.model.Milestone;
import swp.studentprojectportal.model.Project;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.IssueService;
import swp.studentprojectportal.service.servicesimpl.StudentClassService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class StudentIssueController {
    @Autowired
    IssueService issueService;

    @GetMapping("student/issue/list")
    public String IssueList(Model model, HttpSession session,
                            @RequestParam(defaultValue = "") String search,
                            @RequestParam(defaultValue = "-1") int projectId,
                            @RequestParam(defaultValue = "-1") int milestoneId,
                            @RequestParam(defaultValue = "-1") int assigneeId
    ) {
        User user = (User) session.getAttribute("user");
        List<Issue> issueList = issueService.getAllIssueByStudentId(user.getId());

        // Get unique group names from all issues
        Set<Project> uniqueGroups = issueList.stream()
                .map(issue -> issue.getProject())
                .collect(Collectors.toSet());

        // Get unique milestone titles from all issues
        Set<Milestone> uniqueMilestones = issueList.stream()
                .map(issue -> issue.getMilestone())
                .collect(Collectors.toSet());


        // filter issue
        issueList = issueService.filterIssue(issueList, projectId, milestoneId, assigneeId, "");

        // Asignee must all be in filtered issue list
        Set<User> uniqueAssignees = issueList.stream()
                .map(issue -> issue.getAssignee())
                .collect(Collectors.toSet());

        // add attribute for select option
        model.addAttribute("projectId", projectId);
        model.addAttribute("milestoneId", milestoneId);
        model.addAttribute("assigneeId", assigneeId);

        model.addAttribute("groups", uniqueGroups);
        model.addAttribute("milestones", uniqueMilestones);
        model.addAttribute("assignees", uniqueAssignees);
        model.addAttribute("issueList", issueList);
        return "student/issueList";
    }
}
