package swp.studentprojectportal.controller.class_manager;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.service.servicesimpl.ProjectService;
import swp.studentprojectportal.service.servicesimpl.StudentClassService;
import swp.studentprojectportal.service.servicesimpl.UserService;

@Controller
@RequestMapping("/class-manager/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;
    @Autowired
    ClassService classService;
    @Autowired
    StudentClassService studentClassService;
    @Autowired
    UserService userService;

    @GetMapping("/list/")
    public String projectListAll(Model model, HttpSession session) {

        User user = (User)session.getAttribute("user");
        model.addAttribute("projectList", projectService.findAllByClassManagerId(user.getId()));

        return "class_manager/project/projectList";
    }

    @GetMapping("/list/{classId}")
    public String projectList(Model model,
                              @PathVariable Integer classId) {

        model.addAttribute("projectList", projectService.findAllByClassId(classId));
        model.addAttribute("class", classService.getClass(classId));

        return "class_manager/project/projectList";
    }

    @GetMapping("/details/{projectId}")
    public String details(Model model,
                         @PathVariable int projectId) {

        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("projectMemberList", studentClassService.findAllByProjectId(projectId));
        model.addAttribute("projectMentorList", userService.findAllProjectMentor());

        return "class_manager/project/projectDetails";
    }

    @GetMapping("/add")
    public String add(Model model, HttpSession session) {
        User user = (User)session.getAttribute("user");

        model.addAttribute("projectMentorList", userService.findAllProjectMentor());
        model.addAttribute("classList", classService.findAllByClassManagerId(user.getId()));

        return "class_manager/project/projectAdd";
    }

    @PostMapping("/add")
    public String add(@RequestParam String title,
                      @RequestParam String groupName,
                      @RequestParam String description,
                      @RequestParam int classId) {



        return "redirect:./details/";
    }

}
