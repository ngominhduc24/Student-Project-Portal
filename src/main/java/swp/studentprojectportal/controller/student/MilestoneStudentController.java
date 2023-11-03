package swp.studentprojectportal.controller.student;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swp.studentprojectportal.model.Milestone;
import swp.studentprojectportal.model.Submission;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/student")
public class MilestoneStudentController {
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    MilestoneService milestoneService;
    @Autowired
    ProjectService projectService;
    @Autowired
    IssueService issueService;
    @Autowired
    SubmissionService submissionService;
    @Autowired
    SubmitIssueService submitIssueService;

    @GetMapping("/milestone/list/")
    public String milestoneList(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");

        model.addAttribute("projectList", projectService.findAllByStudentUserId(user.getId()));
        model.addAttribute("isMentor", user.getSetting().getId() == 4);
        model.addAttribute("milestoneList", milestoneService.findAllByStudentId(user.getId()));
        return "project_mentor/milestone/milestoneList";
    }

    @GetMapping("/milestone/list/{projectId}")
    public String milestoneList(HttpSession session, Model model, @PathVariable Integer projectId) {

        User user = (User) session.getAttribute("user");

        model.addAttribute("projectList", projectService.findAllByStudentUserId(user.getId()));
        model.addAttribute("isMentor", user.getSetting().getId() == 4);
        model.addAttribute("milestoneList", milestoneService.findAllByProjectId(projectId));
        return "project_mentor/milestone/milestoneList";

    }

    @GetMapping("/milestone/submit/{milestoneId}")
    public String milestoneSubmit(Model model, @PathVariable Integer milestoneId) {

        model.addAttribute("issueList", issueService.findAllByMilestoneId(milestoneId));
        model.addAttribute("milestone", milestoneService.findMilestoneById(milestoneId));

        return "student/milestone/milestoneSubmit";
    }

    @PostMapping("/milestone/submit/")
    public String milestoneSubmitPost(HttpSession session,
                                      @RequestParam("milestoneId") Integer milestoneId,
                                      @RequestParam("file") MultipartFile file,
                                      @RequestParam("note") String note,
                                      @RequestParam("issueId") List<Integer> issueIds,
                                      @RequestParam("assignee") List<Integer> assigneeIds) {
        // check empty
        if(issueIds == null) issueIds = new ArrayList<>();
        if(assigneeIds == null) assigneeIds = new ArrayList<>();

        User user = (User) session.getAttribute("user");
        Milestone milestone = milestoneService.findMilestoneById(milestoneId);

        //update issue assignee
        for (int i=0; i<issueIds.size(); i++)
            issueService.updateIssueAssignee(issueIds.get(i), assigneeIds.get(i));

        //add new submission
        Submission submission = submissionService.insertSubmission(milestoneId, note, user);

        //upload file
        submissionService.updateFileLocation(submission.getId(), saveFile(file, milestoneId + "/" + submission.getId()));

        //add many submit issue
        for (int issueId : issueIds)
            submitIssueService.insertSubmitIssue(issueId, submission.getId());


        return "redirect:../list/";
    }

    private String saveFile(MultipartFile file, String folderName) {

        try {
            String uploadFolder = resourceLoader.getResource("classpath:").getFile().getAbsolutePath();
            uploadFolder += "/static/submission/" + folderName + "/";

            // Create folder if not exist
            File folder = new File(uploadFolder);
            if(!folder.exists()) folder.mkdirs();

            // Generate unique file name
            String fileName = file.getOriginalFilename();
            byte[] bytes = file.getBytes();

            Path uploadPath = Paths.get(uploadFolder + fileName);

            Files.write(uploadPath, bytes);

            return fileName;
        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }

}
