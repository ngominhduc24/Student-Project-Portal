package swp.studentprojectportal.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class userController {

    @GetMapping("/user")
    public String userList() {

        return "admin/userList";
    }

    @GetMapping("/addUser")
    public String addUser() {

        return "admin/addUser";
    }

    @GetMapping("/userDetails")
    public String userDetails() {

        return "admin/userDetails";
    }
}
