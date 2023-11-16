package swp.studentprojectportal.controller.class_manager;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swp.studentprojectportal.model.*;
import swp.studentprojectportal.model.Class;
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

        projectService.update(projectId,title,groupName,description,mentorId,leaderId);
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

    @GetMapping("/freezeAll/{classId}")
    public String freezeAll(@PathVariable int classId) {
        List<Project> projectList = projectService.findAllByClassId(classId);

        for (Project project : projectList)
            projectService.updateStatus(project.getId(), true);

        return "redirect:../list/" + classId;
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

    @GetMapping("/members")
    public String projectMember(Model model, @RequestParam Integer projectId,
                                @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "-1") Integer status,
                                @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "1") Integer sortType) {
        Page<User> studentList = studentClassService.filter(projectId, search, pageNo, pageSize, sortBy, sortType, status);
        model.addAttribute("project", projectService.findById(projectId));
        model.addAttribute("studentList", studentList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("status", status);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("totalPage", studentList.getTotalPages());
        return "class_manager/project/projectMemberList";
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
        Class aclass = classService.findById(classId);
        List<Project> projectList = projectService.findAllByClassId(classId);
        Map<String, Project> projectMap = projectMapping(projectList);

        List<List<String>> data = new SheetHandle().importSheet(file);
        for(List<String> row : data) {
            try {

                //find by email
                User user = userService.findByEmail(row.get(0));

                //find by phone
                if (user == null) {
                    user = userService.findByPhone(row.get(1));

                    if (user == null) continue;
                }

                int projectId = -1;
                //check existed group name
                if(projectMap.containsKey(row.get(2)))
                    projectId = projectMap.get(row.get(2)).getId();
                else {
                    //add new project
                    projectId = projectService.addNewProject(null, row.get(2),
                            null, classId, aclass.getUser().getId()).getId();
                    projectMap.put(row.get(2), projectService.findById(projectId));
                }

                StudentClass studentClass = studentClassService.findByStudentIdAndAclassId(user.getId(), classId);
                studentClassService.updateProjectId(studentClass.getId(), projectId);
            } catch (Exception e) {
                System.out.println("Import Student project: " + e);
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
