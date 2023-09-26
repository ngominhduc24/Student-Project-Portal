package swp.studentprojectportal.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserApiController {
    @Autowired
    UserService userService;

    @GetMapping("/user")
    public List<User> getUserPost(
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize)
    {
        return userService.getUser(pageNo, pageSize);
    }

    @GetMapping("/user/{id}")
    public Optional<User> getUserById(@PathVariable("id") Integer id) {
        return userService.findUserById(id);
    }

}
