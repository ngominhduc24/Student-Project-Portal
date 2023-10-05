package swp.studentprojectportal.controller.class_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class StudentAPIController {
    @Autowired
    UserService userService;

    @GetMapping("/student")
    public List<User> getStudent(
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "search", defaultValue = "") String search)
    {
        final int roleId = 1;
        if(pageNo < 0 || pageSize < 0) return null;
        return userService.getUser(pageNo, pageSize, search.trim(), roleId);
    }
}
