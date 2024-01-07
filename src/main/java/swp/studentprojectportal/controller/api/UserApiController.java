package swp.studentprojectportal.controller.api;

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
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "roleId", defaultValue = "-1") Integer roleId,
            @RequestParam(name = "status", defaultValue = "-1") Integer status,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortType", defaultValue = "1") Integer sortType)
    {
        if(pageNo < 0 || pageSize < 0) return null;
        return userService.getUser(pageNo, pageSize, search.trim(), roleId, status, sortBy, sortType);
    }

    @GetMapping("/user/{id}")
    public Optional<User> getUserById(@PathVariable("id") Integer id) {
        return userService.findUserById(id);
    }

}
