package swp.studentprojectportal.controller.admin;

import org.springframework.web.bind.annotation.*;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.services.servicesimpl.SubjectSevices;

@RestController
public class subjectController {
    @GetMapping("/admin/subject")
    public String subjectPage() {
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
    public Subject addSubject(@RequestBody Subject subject) {
        return services.saveSubject(subject);
    }

    @PutMapping ("/admin/subject/edit")
    public Subject updateSubject(@RequestBody Subject subject) {
        return services.updateSubject(subject);
    }
}


