package swp.studentprojectportal.controller.common;

import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swp.studentprojectportal.service.servicesimpl.RegisterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import swp.studentprojectportal.service.servicesimpl.UserService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.User;
import org.springframework.ui.Model;
import swp.studentprojectportal.utils.Validate;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class ProfileController {
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    RegisterService registerService;
    @Autowired
    UserService userService;

    @GetMapping(path = "/profile")
    public String profilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("errmsg", "You need login before view profile!");
            return "login";
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping(path = "/profile")
    public String updateUser(WebRequest request, HttpSession session,
                             Model model, @RequestParam MultipartFile image) {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String avatarUrl = saveFile(image);
        User user = (User) session.getAttribute("user");
        user.setAvatarUrl(avatarUrl);
        user.setPhone(phone);
        user.setEmail(email);
        user.setFullName(fullName);
        session.setAttribute("user", user);
        if (!Validate.validEmail(email) || !Validate.validPhoneNumber(phone) || !Validate.validFullname(fullName) || !Validate.validAvatarurl(avatarUrl)) {
            model.addAttribute("errmsg", "Update fail!");
        } else {
            userService.saveUser(user);
            model.addAttribute("errmsg", "Update success!");

        }
        model.addAttribute("user", user);
        return "profile";
    }

    // Update mail and phone number with verify
    @GetMapping("/update-mail")
    public String updateEmailPage(Model model) {
        return "mailUpdate";
    }

    @PostMapping("/update-mail")
    public String updateEmail(Model model, WebRequest request, HttpSession session) {
        String newMail = request.getParameter("new_mail");
        User user = (User) session.getAttribute("user");

        if (Validate.validEmail(newMail) == false) {
            model.addAttribute("errmsg", "email is not correct!");
            return "mailUpdate";
        }
        if (userService.checkEmailDomain(newMail) == false) {
            model.addAttribute("errmsg", "email domain is not accept!");
            return "mailUpdate";
        }
        if (userService.checkExistMail(newMail) == true) {
            model.addAttribute("errmsg", "email already exist!");
            return "mailUpdate";
        }
        if (newMail.equals(user.getEmail())) {
            model.addAttribute("errmsg", "email can not be equal old email!");
            return "mailUpdate";
        }

        user.setEmail(newMail);

        session.setAttribute("verifyMail", true);
        session.setAttribute("userauthen", user);
        session.setAttribute("username", newMail);
        session.setAttribute("href", "verifynewusername");
        return "redirect:/verifypage";
    }

    // Update mail and phone number with verify
    @GetMapping("/update-phone")
    public String updatePhonePage(Model model) {
        return "phoneUpdate";
    }

    @PostMapping("/update-phone")
    public String updatePhone(Model model, WebRequest request, HttpSession session) {
        String newPhone = request.getParameter("new_phone");
        User user = (User) session.getAttribute("user");

        if (Validate.validPhoneNumber(newPhone) == false) {
            model.addAttribute("errmsg", "Phone number is not correct!");
            return "phoneUpdate";
        }
        if (userService.checkExistPhoneNumber(newPhone) == true) {
            model.addAttribute("errmsg", "Phone number already exist!");
            return "phoneUpdate";
        }
        if (newPhone.equals(user.getPhone())) {
            model.addAttribute("errmsg", "Phone number can not be equal old phone number!");
            return "phoneUpdate";
        }

        user.setPhone(newPhone);

        session.setAttribute("verifyMail", false);
        session.setAttribute("userauthen", user);
        session.setAttribute("username", newPhone);
        session.setAttribute("href", "verifynewusername");
        return "redirect:/verifypage";
    }

    @GetMapping("/verifynewusername")
    public String updateNewUserName(Model model, HttpSession session, @RequestParam("key") String token) {
        boolean verifyMail = (boolean) session.getAttribute("verifyMail");
        String username = (String) session.getAttribute("username");
        User user = registerService.verifyToken(token);
        if (user == null) {
            return "profile";
        }

        if(verifyMail) {
            user.setEmail(username);
        } else {
            user.setPhone(username);
        }
        userService.saveUser(user);
        session.setAttribute("user", user);
        return "redirect:/profile";
    }

    private String saveFile(MultipartFile file) {
        try {
            String uploadFolder = resourceLoader.getResource("classpath:").getFile().getAbsolutePath();
            uploadFolder += "/static/upload/";

            // Create folder if not exist
            File folder = new File(uploadFolder);
            if(!folder.exists()) folder.mkdirs();

            // Generate unique file name
            String fileName = file.getOriginalFilename();
            byte[] bytes = file.getBytes();

            Path uploadPath = Paths.get(uploadFolder + fileName);

            Files.write(uploadPath, bytes);

            return "/upload/" + fileName;
        } catch (Exception e) {
            System.out.println(e);
            return "/images/user_icon.png";
        }
    }
}
