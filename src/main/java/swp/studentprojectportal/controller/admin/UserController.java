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
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.EmailService;
import swp.studentprojectportal.service.servicesimpl.SettingService;
import swp.studentprojectportal.service.servicesimpl.UserService;
import swp.studentprojectportal.utils.Utility;
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
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam int roleId,
            Model model) {

        model.addAttribute("roleList", settingService.getAllRole());
        model.addAttribute("fullName", fullName);
        model.addAttribute("phone", phone);
        model.addAttribute("email", email);
        model.addAttribute("roleId", roleId);

        if(!Validate.validFullname(fullName)) {
            model.addAttribute("error", "Invalid Full Name!");
            return "admin/user/userAdd";
        }

        if(email.isEmpty() && phone.isEmpty()) {
            model.addAttribute("error", "Please input email or phone number!");
            return "admin/user/userAdd";
        }

        //check email
        if(!email.isEmpty() && !Validate.validEmail(email)) {
            model.addAttribute("error", "Email is invalid!");
            return "admin/user/userAdd";
        }

        if(!email.isEmpty() && userService.checkExistMail(email)) {
            model.addAttribute("error", "Email already exist!");
            return "admin/user/userAdd";
        }

        if(!email.isEmpty() && !userService.checkEmailDomain(email)) {
            model.addAttribute("error", "Email domain is not allowed!");
            return "admin/user/userAdd";
        }

        //check phone
        if(!phone.isEmpty() && !Validate.validPhoneNumber(phone)) {
            model.addAttribute("error", "Phone is invalid!");
            return "admin/user/userAdd";
        }

        if(!phone.isEmpty() && userService.checkExistPhoneNumber(phone)) {
            model.addAttribute("error", "Phone already exist!");
            return "admin/user/userAdd";
        }

        User user = new User();
        user.setActive(false);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setSetting(settingService.findById(roleId));

        int newUserId =  userService.saveUser(user).getId();

        //if add with email -> send mail
        if(!email.isEmpty()) {
            String token = RandomString.make(30); // generate token

            // gen token
            user.setToken(token);

            // send mail
            String href = "reset-password";
            String tokenSender = Utility.getSiteURL() + "/" + href + "?key=" + token;

            emailservice.sendEmail(user.getFullName(), user.getEmail(), tokenSender);
        }

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

//    private String checkValidateUser(String email, String phone) {
//        email = email.trim();
//        phone = phone.trim();
//
//        if (email.isEmpty() && phone.isEmpty()) return "Please input email or phone number";
//        if (!email.isEmpty() && !userService.checkEmailDomain(email)) return "Invalid email domain";
//
//        if (!email.isEmpty() && !Validate.validEmail(email)) return "Invalid email";
//        if (!phone.isEmpty() && !Validate.validPhoneNumber(phone)) return "Invalid phone number";
//
//        if (!email.isEmpty() && userService.checkExistMail(email)) return "Email existed!";
//        if (!phone.isEmpty() && userService.checkExistPhoneNumber(phone)) return "Phone number existed!";
//
//        return null;
//    }

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
