package swp.studentprojectportal.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import swp.studentprojectportal.model.StudentClass;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.StudentClassService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProjectApiController {
    @Autowired
    StudentClassService studentClassService;

    @GetMapping("/project/member/{projectId}")
    public List<User> getAllMemberOfGroup(@PathVariable Integer projectId) {
        return studentClassService.findAllByProjectId(projectId).stream().map(StudentClass::getStudent).toList();
    }
}
