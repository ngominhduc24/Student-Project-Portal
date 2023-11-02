package swp.studentprojectportal.controller.common;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import swp.studentprojectportal.model.User;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @GetMapping("/class/{classId}")
    public String chatClass(Model model, @PathVariable Integer classId, HttpSession session) {
        User user = (User) session.getAttribute("user");

        model.addAttribute("username", user.getFullName());
        model.addAttribute("classId", classId);

        return "common/chat";
    }

}
