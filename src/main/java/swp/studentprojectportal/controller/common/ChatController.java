package swp.studentprojectportal.controller.common;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.ClassService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    ClassService classService;

    @GetMapping("/class/")
    public String chatClass(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Class> classList = new ArrayList<>();

        // class manager
        if (user.getSetting().getId() == 4)
            classList = classService.findAllByClassManagerId(user.getId());

        // student
        if (user.getSetting().getId() == 1)
            classList = classService.findAllByStudentUserId(user.getId());

        return "redirect:./" + classList.get(0).getId();
    }

    @GetMapping("/class/{classId}")
    public String chatClass(Model model, @PathVariable Integer classId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Class> classList = new ArrayList<>();

        // class manager
        if (user.getSetting().getId() == 4)
            classList = classService.findAllByClassManagerId(user.getId());

        // student
        if (user.getSetting().getId() == 1)
            classList = classService.findAllByStudentUserId(user.getId());

        model.addAttribute("classList", classList);
        model.addAttribute("username", user.getFullName());
        model.addAttribute("classId", classId);

        return "common/chat";
    }

}
