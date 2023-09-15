package swp.studentprojectportal.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.repository.ISubjectRepository;
import swp.studentprojectportal.services.servicesimpl.SubjectSevice;

import java.util.List;

@Controller
public class subjectController {
    @Autowired
    SubjectSevice subjectSevice;

    @Autowired
    private ISubjectRepository IsubjectRepository;

    private SubjectSevice services;

    @GetMapping("/admin/subject")
    public String subjectPage(Model model) {
        List<Subject> subjectList = subjectSevice.getAllSubjects();
        model.addAttribute("SubjectList", subjectList);
        return "admin/subjectList";
    }

    @GetMapping("/admin/subject/add")
    public String createSubjectPage() {
        return "admin/addSubject";
    }

    @PostMapping("/admin/subject/add")
    public Subject CreateSubject(Subject subject) {
        services.saveSubject(subject);
        return subject;
    }

    @GetMapping("/admin/subject/edit")
    public String editSubjectPage() {
        return "admin/subjectDetail";
    }


    @PutMapping ("/admin/subject/edit")
    public Subject updateSubject(@RequestBody Integer Id, Subject subject) {
        return services.updateSubject(Id, subject);
    }
}




