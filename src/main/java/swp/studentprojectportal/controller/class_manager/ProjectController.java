package swp.studentprojectportal.controller.class_manager;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.Project;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.service.servicesimpl.ProjectService;
import swp.studentprojectportal.service.servicesimpl.StudentClassService;
import swp.studentprojectportal.service.servicesimpl.UserService;
import swp.studentprojectportal.utils.SheetHandle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        int firstClassId = classService.findAllByClassManagerId(user.getId()).get(0).getId();

        return "redirect:./" + firstClassId;
    }

    @GetMapping("/list/{classId}")
    public String projectList(Model model,
                              @PathVariable Integer classId,
                              HttpSession session) {
        User user = (User)session.getAttribute("user");

        model.addAttribute("projectList", projectService.findAllByClassId(classId));
        model.addAttribute("classList", classService.findAllByClassManagerId(user.getId()));
        model.addAttribute("class", classService.getClass(classId));
        model.addAttribute("studentList", studentClassService.findAllByClassId(classId));

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

    @PostMapping("/update")
    public String update(Model model,
                         @RequestParam int projectId,
                         @RequestParam String title,
                         @RequestParam String groupName,
                         @RequestParam String description,
                         @RequestParam int mentorId,
                         @RequestParam int leaderId) {

        Project project = projectService.update(projectId,title,groupName,description,mentorId,leaderId);

        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("projectMemberList", studentClassService.findAllByProjectId(projectId));
        model.addAttribute("projectMentorList", userService.findAllProjectMentor());
        model.addAttribute("msg", "Update success!");

        return "class_manager/project/projectDetails";
    }

    @GetMapping("/add")
    public String add(Model model, HttpSession session) {
        User user = (User)session.getAttribute("user");

//        model.addAttribute("projectMentorList", userService.findAllProjectMentor());
        model.addAttribute("classList", classService.findAllByClassManagerId(user.getId()));

        return "class_manager/project/projectAdd";
    }

    @PostMapping("/add")
    public String add(Model model,
                      @RequestParam String title,
                      @RequestParam String groupName,
                      @RequestParam String description,
                      @RequestParam int classId,
                      HttpSession session) {
        User user = (User)session.getAttribute("user");

        //check duplicate group name
        if (projectService.checkDuplicateGroupNameInClass(classId, groupName)!=null) {
            model.addAttribute("classList", classService.findAllByClassManagerId(user.getId()));

            model.addAttribute("title", title);
            model.addAttribute("groupName", groupName);
            model.addAttribute("description", description);
            model.addAttribute("classId", classId);

            model.addAttribute("error", "Duplicate group name in class!");
            return "class_manager/project/projectAdd";
        }

        //add new
        Project project = projectService.addNewProject(title,groupName, description, classId, user.getId());

        //model attr
        model.addAttribute("project", projectService.findById(project.getId()));
        model.addAttribute("projectMemberList", studentClassService.findAllByProjectId(project.getId()));
        model.addAttribute("projectMentorList", userService.findAllProjectMentor());
        model.addAttribute("msg", "Add new success!");

        return "class_manager/project/projectDetails";
    }

    @GetMapping("/freeze/{projectId}")
    public String freeze(@PathVariable int projectId) {
        Project project = projectService.updateStatus(projectId, true);

        return "redirect:../details/" + project.getId();
    }

    @GetMapping("/delete/{projectId}")
    public String delete(@PathVariable int projectId) {

        if (projectService.deleteById(projectId))
            return "redirect:../list/";
        else {

            //add model?

            return "redirect:../details/" + projectId;
        }
    }

    @GetMapping("/member/{classId}")
    public String member(Model model,
                          @PathVariable Integer classId) {

        model.addAttribute("projectList", projectService.findAllByClassId(classId));
        model.addAttribute("class", classService.getClass(classId));
        model.addAttribute("studentList", studentClassService.findAllByClassId(classId));
        model.addAttribute("noGroupStudentList", studentClassService.findAllNoGroupInClass(classId));

        return "class_manager/project/projectMember";
    }

    @GetMapping("/arrange/{classId}")
    public String arrange(Model model,
                          @PathVariable Integer classId) {

        model.addAttribute("projectList", projectService.findAllByClassId(classId));
        model.addAttribute("class", classService.getClass(classId));
        model.addAttribute("studentList", studentClassService.findAllByClassId(classId));
        model.addAttribute("noGroupStudentList", studentClassService.findAllNoGroupInClass(classId));

        return "class_manager/project/projectArrange";
    }

    @GetMapping("/arrange-member/{studentId}/{projectId}")
    public String arrangeMember(@PathVariable Integer studentId,
                                @PathVariable Integer projectId) {

        studentClassService.updateProjectId(studentId, projectId);

        return "redirect:/";
    }

    @GetMapping("/setLeader/{studentId}/{projectId}")
    public String setLeader(@PathVariable Integer studentId,
                            @PathVariable Integer projectId) {

        projectService.setLeader(studentId, projectId);

        return "redirect:/";
    }

    @GetMapping("/updateNote/{studentId}/{note}")
    public String updateNote(@PathVariable Integer studentId,
                             @PathVariable String note) {

        projectService.updateNote(studentId, note);

        return "redirect:/";
    }

    @PostMapping("/importStudent")
    public String importStudent(@RequestParam MultipartFile file,
                                @RequestParam int classId) {
        List<Project> projectList = projectService.findAllByClassId(classId);
        Map<String, Project> projectMap = projectMapping(projectList);

        List<List<String>> data = new SheetHandle().importSheet(file);
        for(List<String> row : data) {
            try {
                int studentId = Integer.parseInt(row.get(0));
                int projectId = projectMap.get(row.get(2)).getId();

                studentClassService.updateProjectId(studentId, projectId);
            } catch (Exception e) {
                System.out.println(e);
            }

        }

        return "redirect:./list/" + classId;
    }

    private Map<String, Project> projectMapping(List<Project> projectList) {
        Map<String, Project> map = new HashMap<>();

        for (Project project : projectList)
            map.put(project.getGroupName(), project);

        //setting no group
        Project project = new Project();
        project.setId(-1);
        map.put("No group", project);

        return map;
    }

}
