package swp.studentprojectportal.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.services.servicesimpl.SubjectSevices;

import java.util.List;

@Controller
public class subjectController {
    @Autowired
    SubjectSevices subjectSevices;

    @GetMapping("/admin/subject")
    public String subjectPage(Model model) {
        List<Subject> subjectList = subjectSevices.getAllSubjects();
        model.addAttribute("SubjectList", subjectList);
        return "admin/subjectList";
    }

    @GetMapping("/admin/subject/add")
    public String addSubjectPage() {
        return "admin/addSubject";
    }

    @GetMapping("/admin/subject/edit")
    public String editSubjectPage() {
        return "admin/subjectDetail";
    }

    private SubjectSevices services;

    @PostMapping("/admin/subject/add")
    public Subject addSubject(@RequestBody WebRequest request, Subject subject) {
        String subjectName =request.getParameter("subjectName");
        String subjectCode =request.getParameter("subjectCode");
        String subjectDescription =request.getParameter("subjectDescription");
        String subjectManager = request.getParameter("subjectManager");
        return services.saveSubject(subject);
    }

    @PutMapping ("/admin/subject/edit")
    public Subject updateSubject(@RequestBody Integer Id, Subject subject) {
        return services.updateSubject(Id, subject);
    }
}




