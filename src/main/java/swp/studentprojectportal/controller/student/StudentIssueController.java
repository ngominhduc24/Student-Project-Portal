package swp.studentprojectportal.controller.student;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Issue;
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
        issueList = issueService.filterIssue(issueList, -1, -1, -1, "3");

        // Get unique group names from all issues
        Set<String> uniqueGroups = issueList.stream()
                .map(issue -> issue.getProject().getGroupName())
                .collect(Collectors.toSet());

        // Get unique milestone titles from all issues
        Set<String> uniqueMilestones = issueList.stream()
                .map(issue -> issue.getMilestone().getTitle())
                .collect(Collectors.toSet());

        // Get unique assignee display names from all issues
        Set<String> uniqueAssignees = issueList.stream()
                .map(issue -> issue.getAssignee().getDisplayName())
                .collect(Collectors.toSet());

        // filter issue

        model.addAttribute("groups", uniqueGroups);
        model.addAttribute("milestones", uniqueMilestones);
        model.addAttribute("assignees", uniqueAssignees);
        model.addAttribute("issueList", issueList);
        return "student/issueList";
    }
}
