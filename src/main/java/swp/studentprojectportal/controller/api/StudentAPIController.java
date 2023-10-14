package swp.studentprojectportal.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swp.studentprojectportal.model.Project;
import swp.studentprojectportal.model.StudentClass;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.IStudentClassRepository;
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.service.servicesimpl.StudentClassService;
import swp.studentprojectportal.service.servicesimpl.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class StudentAPIController {
    @Autowired
    UserService userService;
    @Autowired
    StudentClassService studentClassService;
    @Autowired
    ClassService classService;
    @Autowired
    IStudentClassRepository studentClassRepository;

    @GetMapping("/student")
    public List<User> getStudent(
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "search", defaultValue = "") String search)
    {
        final int roleId = 1;
        final int status = 1;
        if(pageNo < 0 || pageSize < 0) return null;
        return userService.getUser(pageNo, pageSize, search.trim(), roleId, status);
    }

    @GetMapping("/student/checkClass")
    public ResponseEntity checkStudentInClass(
            @RequestParam(name = "classId") Integer classId,
            @RequestParam(name = "studentId") Integer studentId) {
        boolean result =  studentClassService.checkStudentInClass(classId, studentId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("student/addStudentToClass")
    public ResponseEntity addStudentToClass(
            @RequestParam(name = "classId") Integer classId,
            @RequestParam(name = "studentId") Integer studentId) {
        boolean result =  studentClassService.addNewStudentToClass(classId, studentId);
        return ResponseEntity.ok().body(result);
    }
}
