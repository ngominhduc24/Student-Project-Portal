package swp.studentprojectportal.controller.project_mentor;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Project;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.service.servicesimpl.ProjectService;
import swp.studentprojectportal.service.servicesimpl.SubjectService;

import java.util.List;

@Controller
public class ProjectMentorProjectController {
    @Autowired
    ProjectService projectService;
    @Autowired
    ClassService classService;
    @Autowired
    SubjectService subjectService;
    @GetMapping("/project-mentor/project")
    public String projectListForMentor(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
                                       @RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "-1") Integer classId,
                                       @RequestParam(defaultValue = "-1") Integer status, @RequestParam(defaultValue = "id") String sortBy,
                                       @RequestParam(defaultValue = "1") Integer sortType, @RequestParam(defaultValue = "-1") Integer subjectId,
                                       Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");
        List<Class> classList = classService.findClassForProject(user.getId());
        List<Subject> subjectList = subjectService.findSubjectForProject(user.getId());
        Page<Project> projectList = projectService.filterByProjectMentor(user.getId(), search, pageNo, pageSize, sortBy, sortType, classId, subjectId, status);
        model.addAttribute("projectList", projectList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("status", status);
        model.addAttribute("classId", classId);
        model.addAttribute("subjectId", subjectId);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("totalPage", projectList.getTotalPages());
        model.addAttribute("classList", classList);
        model.addAttribute("subjectList", subjectList);
        return "project_mentor/projectList";
    }
}
