package swp.studentprojectportal.controller.subject_manager.subject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SubjectHome {
    @GetMapping("/subject-manager/subject")
    public String subjectPage(Model model){
        return "/subject_manager/subject/subjectHome";
    }
}
