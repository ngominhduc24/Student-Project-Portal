package swp.studentprojectportal.controller.student;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Milestone;
import swp.studentprojectportal.model.Project;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.MilestoneService;
import swp.studentprojectportal.service.servicesimpl.ProjectService;
import swp.studentprojectportal.service.servicesimpl.SettingService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentHomeController {
    @Autowired
    SettingService settingService;
    @Autowired
    ProjectService projectService;
    @Autowired
    MilestoneService milestoneService;

    @GetMapping("student/home")
    public String dashboard(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "6") Integer pageSize,
                            Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Page<Project> projectList = projectService.filterByStudendDashboard(user.getId(), pageNo, pageSize);

        // bar
        List<String> barLabel = new ArrayList<>();
        List<Integer> barCount = new ArrayList<>();

        for (Project project : projectList) {
            barLabel.add(project.getAclass().getClassName());
            barCount.add(milestoneService.findAllBySubjectAndClassOfProject(project.getAclass().getId()).size());
        }

        // donut
        List<Milestone> milestoneList = milestoneService.findAllByStudentId(user.getId());
        int countAllAssignment = milestoneList.size();
        int countPending = (int) milestoneList.stream().filter(m -> m.getSubmissionList().isEmpty()).count();
        int countSubmitted = countAllAssignment - countPending;

        model.addAttribute("projects", projectList);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentSemester", settingService.getLastestSemester().getSettingTitle());
        model.addAttribute("totalPage", projectList.getTotalPages());

        model.addAttribute("barLabel", barLabel);
        model.addAttribute("barCount", barCount);

        model.addAttribute("countAllAssignment", countAllAssignment);
        model.addAttribute("countPending", countPending);
        model.addAttribute("countSubmitted", countSubmitted);

        return "student/studentDashboard";
    }
}
