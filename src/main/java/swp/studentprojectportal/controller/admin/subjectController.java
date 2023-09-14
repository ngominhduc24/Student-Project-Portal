package swp.studentprojectportal.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class subjectController {
    @RequestMapping("/admin/subject")
    public String subjectPage() {
        return "admin/subjectList";
    }

    @RequestMapping("/admin/subject/add")
    public String addSubjectPage() {
        return "admin/addSubject";
    }

    @RequestMapping("/admin/subject/edit")
    public String editSubjectPage() {
        return "admin/subjectDetail";
    }
}


