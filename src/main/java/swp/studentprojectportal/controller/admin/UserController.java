package swp.studentprojectportal.controller.admin;

import jakarta.servlet.http.HttpSession;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.controller.common.authentication.VerifyController;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.EmailService;
import swp.studentprojectportal.service.servicesimpl.SettingService;
import swp.studentprojectportal.service.servicesimpl.UserService;
import swp.studentprojectportal.utils.Utility;
import swp.studentprojectportal.utils.Validate;

import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private int adminRoleId;

    @Autowired
    UserService userService;

    @Autowired
    SettingService settingService;

    @Autowired
    EmailService emailservice;

    @GetMapping("/user")
    public String userList(Model model,
            @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("page", page);
        model.addAttribute("totalPage", userService.getTotalPage(10));
        model.addAttribute("roleList", settingService.getAllRole());
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
            @RequestParam String username,
            @RequestParam int roleId,
            Model model) {

        User user = new User();
        user.setActive(false);
        user.setFullName(fullName);

        if(Validate.validEmail(username)) {
            user.setEmail(username);
        }

        if(!Validate.validFullname(fullName)) {
            model.addAttribute("error", "Invalid Full Name!");
            return "redirect:/admin/user/userAdd";

        }

        if(user.getEmail() == null) {
            model.addAttribute("error", "Email is invalid!");
            return "redirect:/admin/user/userAdd";

        }

        if(user.getEmail() != null && userService.checkExistMail(user.getEmail())) {
            model.addAttribute("error", "Email already exist!");
            return "redirect:/admin/user/userAdd";

        }

        if(user.getEmail() != null &&!userService.checkEmailDomain(user.getEmail())) {
            model.addAttribute("error", "Email domain is not allowed!");
            return "redirect:/admin/user/userAdd";

        }

        user.setSetting(settingService.findById(roleId));

        String token = RandomString.make(30); // genarate token

        user.setToken(token);
        int newUserId =  userService.saveUser(user).getId();

        // get href
        String href = "reset-password";
        String token_sender = Utility.getSiteURL() + "/" + href + "?key=" + token;

        emailservice.sendEmail(user.getFullName(), user.getEmail(), token_sender);
        model.addAttribute("email", user.getEmail());

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
        String msg = checkValidateUpdateUser(email, phone, userUpdate);
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

    private String checkValidateUser(String email, String phone) {
        email = email.trim();
        phone = phone.trim();

        if (email.isEmpty() && phone.isEmpty()) return "Please input email or phone number";
        if (!email.isEmpty() && !userService.checkEmailDomain(email)) return "Invalid email domain";

        if (!email.isEmpty() && !Validate.validEmail(email)) return "Invalid email";
        if (!phone.isEmpty() && !Validate.validPhoneNumber(phone)) return "Invalid phone number";

        if (!email.isEmpty() && userService.checkExistMail(email)) return "Email existed!";
        if (!phone.isEmpty() && userService.checkExistPhoneNumber(phone)) return "Phone number existed!";

        return null;
    }

    private String checkValidateUpdateUser(String email, String phone, User user) {
        email = email.trim();
        phone = phone.trim();

        if (email.isEmpty() && phone.isEmpty()) return "Please input email or phone number";
        if (!email.isEmpty() && !userService.checkEmailDomain(email)) return "Invalid email domain";

        if (!email.isEmpty() && !Validate.validEmail(email)) return "Invalid email";
        if (!phone.isEmpty() && !Validate.validPhoneNumber(phone)) return "Invalid phone number";

        if (!email.equals(user.getEmail()) && !email.isEmpty() && userService.checkExistMail(email)) return "Email existed!";
        if (!phone.equals(user.getPhone()) && !phone.isEmpty() && userService.checkExistPhoneNumber(phone)) return "Phone number existed!";

        return null;
    }
}
