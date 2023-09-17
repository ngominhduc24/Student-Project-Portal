package swp.studentprojectportal.controller.common;
import org.springframework.web.bind.annotation.GetMapping;
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
    UserService userService;

    @GetMapping(path="/profile")
    public String profilePage(HttpSession session,Model model,WebRequest request){
        User user = (User)session.getAttribute("user");
        if(user == null) {
            model.addAttribute("errmsg","You need login before view profile!");
            return "login";
        }
        model.addAttribute("user",user);
        return "userDetails";
    }
    @RequestMapping(path="/profile")
    public String updateUser(WebRequest request,HttpSession session,Model model){
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        String errmsg;
        User user = (User) session.getAttribute("user");
        if(!Validate.validEmail(email) || !Validate.validPhoneNumber(phone) ){
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
}
