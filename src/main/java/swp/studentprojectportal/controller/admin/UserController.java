package swp.studentprojectportal.controller.admin;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.SettingService;
import swp.studentprojectportal.service.servicesimpl.UserService;
import swp.studentprojectportal.utils.Validate;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private int adminRoleId;

    @Autowired
    UserService userService;

    @Autowired
    SettingService settingService;

    @GetMapping("/user")
    public String userList(Model model,
            @RequestParam(defaultValue = "0") int page) {
//        if (page<0) page = 0;
//        List<User> UserList = userService.getUser(page, 10);

//        model.addAttribute("userList", UserList);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", userService.getTotalPage(10));

        return "admin/user/userList";
    }

    @GetMapping("/addUser")
    public String addUserGet(Model model) {
        model.addAttribute("roleList", settingService.getAllRole());
        return "admin/user/userAdd";
    }

    @PostMapping ("/addUser")
    public String addUser(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam int roleId,
            @RequestParam String password,
            Model model) {

        String errorMsg = checkValidate(email, phone);

        if(errorMsg!=null) {
            model.addAttribute("error", errorMsg);
            model.addAttribute("roleList", settingService.getAllRole());
            return "admin/user/userAdd";
        }

        int newUserId = userService.addUser(fullName, email, phone, password, roleId).getId();
        return "redirect:./userDetails?id=" + newUserId;
    }

    @PostMapping("/updateUser")
    public String updateUser(
            HttpSession session,
            @RequestParam int id,
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam int roleId,
            @RequestParam String note,
            @RequestParam boolean status,
            Model model) {
        User userUpdate = userService.findUserById(id).get();
        model.addAttribute("user", userUpdate);
        model.addAttribute("roleList", settingService.getAllRole());

        //check validate before update
        String msg = checkValidateUpdate(email, phone, userUpdate);
        if (msg != null) {
            model.addAttribute("errorMsg", msg);
        } else {
            //update
            boolean ans = userService.updateUser(id, fullName, email, phone, roleId, status, note);

            if (ans) model.addAttribute("msg", "Update success");
            else model.addAttribute("errorMsg", "Update failed");
        }

        //update session
        if (id == ((User)session.getAttribute("user")).getId() ) {
            User user = userService.findUserById(id).get();
            session.setAttribute("user", user);
            if (user.getSetting().getId() != adminRoleId)
                return "redirect:/home";
        }

        return "admin/user/userDetails";
    }

    @GetMapping("/userDetails")
    public String userDetails(Model model, @RequestParam int id) {
        Optional<User> user = userService.findUserById(id);
        model.addAttribute("user", user.isPresent() ? user.get() : null);
        model.addAttribute("roleList", settingService.getAllRole());
        return "admin/user/userDetails";
    }

    @GetMapping("/updateUserStatus")
    public String updateSettingStatus(
            @RequestParam int id,
            @RequestParam boolean status) {
        userService.updateUserStatus(id, status);
        return "redirect:/";
    }

    private String checkValidate(String email, String phone) {
        if (email.isEmpty() && phone.isEmpty()) return "Please input email or phone number";
        if (!email.isEmpty() && !userService.checkEmailDomain(email)) return "Invalid email domain";

        if (!email.isEmpty() && !Validate.validEmail(email)) return "Invalid email";
        if (!phone.isEmpty() && !Validate.validPhoneNumber(phone)) return "Invalid phone number";

        if (!email.isEmpty() && userService.checkExistMail(email)) return "Email existed!";
        if (!phone.isEmpty() && userService.checkExistPhoneNumber(phone)) return "Phone number existed!";

        return null;
    }

    private String checkValidateUpdate(String email, String phone, User user) {
        if (!email.isEmpty() && !userService.checkEmailDomain(email)) return "Invalid email domain";

        if (!email.isEmpty() && !Validate.validEmail(email)) return "Invalid email";
        if (!phone.isEmpty() && !Validate.validPhoneNumber(phone)) return "Invalid phone number";

        if (!email.equals(user.getEmail()) && !email.isEmpty() && userService.checkExistMail(email)) return "Email existed!";
        if (!phone.equals(user.getPhone()) && !phone.isEmpty() && userService.checkExistPhoneNumber(phone)) return "Phone number existed!";

        return null;
    }
}
