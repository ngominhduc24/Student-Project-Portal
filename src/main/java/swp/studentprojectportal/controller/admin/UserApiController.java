package swp.studentprojectportal.controller.admin;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.services.servicesimpl.UserService;

import java.util.List;

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

}
