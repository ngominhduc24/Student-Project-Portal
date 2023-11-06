package swp.studentprojectportal.controller.student;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.*;
import swp.studentprojectportal.service.servicesimpl.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class StudentIssueController {
    @Autowired
    IssueService issueService;

    @Autowired
    IssueSettingService issueSettingService;

    @Autowired
    UserService userService;

    @Autowired
    MilestoneService milestoneService;

    @GetMapping("student/issue/list")
    public String IssueList(Model model, HttpSession session,
                            @RequestParam(defaultValue = "") String search,
                            @RequestParam(defaultValue = "-1") int projectId,
                            @RequestParam(defaultValue = "-1") int milestoneId,
                            @RequestParam(defaultValue = "-1") int assigneeId,
                            @RequestParam(defaultValue = "-1") int issueId,
                            @RequestParam(defaultValue =  "1") int subjectId
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

        //function to get all process
        List<IssueSetting> processes = issueSettingService.findProcessTitle(subjectId, "process");

        //function to get all status
        List<IssueSetting> status = issueSettingService.findProcessTitle(subjectId, "status");

        //function to get all type
        List<IssueSetting> type = issueSettingService.findProcessTitle(subjectId, "type");

        //Get issue by id
        Issue issue = issueService.getIssueById(issueId);

        // add attribute for select option
        model.addAttribute("projectId", projectId);
        model.addAttribute("milestoneId", milestoneId);
        model.addAttribute("assigneeId", assigneeId);

        model.addAttribute("groups", uniqueGroups);
        model.addAttribute("milestones", uniqueMilestones);
        model.addAttribute("assignees", uniqueAssignees);
        model.addAttribute("issueList", issueList);

        // add attribute for popup detail modal
        model.addAttribute("selectedIssue", issue);
        model.addAttribute("id", issueId);
        model.addAttribute("process", processes);
        model.addAttribute("status", status);
        model.addAttribute("type", type);

//        System.out.println(issueList.get(0).getProject().getAclass().getSubject());
        System.out.println(processes);

        return "student/issueList";
    }

    @PostMapping("/issueUpdate")
    public String issueUpdate(@RequestParam int issueIdPost,
                              @RequestParam String title,
                              @RequestParam int type,
                              @RequestParam int milestone,
                              @RequestParam int assignee,
                              @RequestParam int process,
                              @RequestParam int status,
                              Model model) {
        User user = userService.findById(assignee);
        Milestone milestone1 = milestoneService.findMilestoneById(milestone);
        IssueSetting typeSetting = issueSettingService.findById(type);
        IssueSetting statusSetting = issueSettingService.findById(status);
        IssueSetting processSetting = issueSettingService.findById(process);

        if(title == null) {
            model.addAttribute("errorMsg", "Please enter issue title");
        } else {
            Issue issue = issueService.getIssueById(issueIdPost);
            issue.setTitle(title);
            issue.setAssignee(user);
            issue.setMilestone(milestone1);
            issue.setType(typeSetting);
            issue.setStatus(statusSetting);
            issue.setProcess(processSetting);
            issueService.saveIssue(issue);
        }
        return "redirect:/student/issue/list?issueId=" + issueIdPost;
    }
}
