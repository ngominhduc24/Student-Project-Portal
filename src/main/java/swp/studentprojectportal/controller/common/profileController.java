package swp.studentprojectportal.controller.common;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import swp.studentprojectportal.services.servicesimpl.RegisterService;
import swp.studentprojectportal.services.servicesimpl.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.IUserRepository;
import org.springframework.ui.Model;
import swp.studentprojectportal.utility.Validate;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class profileController {
    @Autowired
    RegisterService registerService;
    @Autowired
    UserService userService;

    @RequestMapping(path="/profile")
    public String profilePage(HttpSession session,Model model,WebRequest request){
        User user = (User)session.getAttribute("user");
        if(user == null) {
            model.addAttribute("errmsg","You need login before view profile!");
            return "redirect:/login";
        }
        model.addAttribute("user",user);
        return "userDetails";
    }
    @RequestMapping(path="/updateProfile")
    public String updateUser(WebRequest request,HttpSession session,Model model){
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        String errmsg;
        User user = (User) session.getAttribute("user");
        if(Validate.validEmail(email) == false || Validate.validPhoneNumber(phone) == false){
            model.addAttribute("errmsg","Update fail!");
        }
        else{
            user.setPhone(phone);
            user.setEmail(email);
            user.setFullName(fullName);
            session.setAttribute("user",user);
            userService.registerNewAccount(user);
            model.addAttribute("errmsg","Update success!");

        }
        model.addAttribute("user",user);
        return "userDetails";
    }


//   Update mail and phone number with verify
    @GetMapping("/update-mail")
    public String updateEmailPage(Model model) {
        return "mailUpdate";
    }

    @PostMapping("/update-mail")
    public String updateEmail(Model model, WebRequest request, HttpSession session) {
        String newMail = request.getParameter("new_mail");
        User user = (User) session.getAttribute("user");
        System.out.println(user);
        if(Validate.validEmail(newMail) == false) {
            model.addAttribute("errmsg", "email is not correct!");
            return "mailUpdate";
        }
        if(userService.checkEmailDomain(newMail) == false) {
            model.addAttribute("errmsg", "email domain is not accept!");
            return "mailUpdate";
        }
        if(userService.checkExistMail(newMail) == true) {
            model.addAttribute("errmsg", "email already exist!");
            return "mailUpdate";
        }
        if(newMail.equals(user.getEmail())) {
            model.addAttribute("errmsg", "email can not be equal old email!");
            return "mailUpdate";
        }

        session.setAttribute("userauthen", user);
        session.setAttribute("newmail", newMail);
        session.setAttribute("href", "verifynewmail");
        return "redirect:/verifypage";
    }

    @GetMapping("/verifynewmail")
    public String registerMail(Model model, HttpSession session,@RequestParam("key") String token) {
        User user = registerService.verifyToken(token);
        if(user == null) {
            return "register";
        }
        String newMail = (String)session.getAttribute("newmail");
        if(newMail != null) user.setEmail(newMail);
        userService.registerNewAccount(user);
        session.setAttribute("user", user);
        return "redirect:/profile";
    }
}
