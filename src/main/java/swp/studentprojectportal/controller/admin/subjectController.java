package swp.studentprojectportal.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.repository.ISubjectRepository;
import swp.studentprojectportal.services.servicesimpl.SubjectSevice;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class subjectController {
    @Autowired
    SubjectSevice subjectSevice;
    @Autowired
    private ISubjectRepository IsubjectRepository;
    @Autowired
    private SubjectSevice services;

    List<Subject> subjectList = new CopyOnWriteArrayList<>();

    @GetMapping("/admin/subject")
    public String subjectPage(Model model) {
        subjectList = subjectSevice.getAllSubjects();
        model.addAttribute("SubjectList", subjectList);
        return "admin/subjectList";
    }

    @GetMapping("/admin/subject/add")
    public String createSubjectPage(Model model) {
        model.addAttribute("subject", new Subject());
        return "admin/subjectAdd";
    }

    @PostMapping("/admin/subject/add")
    public String createSubject(@ModelAttribute Subject subject) {
        subjectList.add(subject);
        return "redirect:/admin/subject";
    }

    @GetMapping("/admin/subject/details")
    public String updateSubjectPage(@RequestParam("id") Integer Id, Model model) {
        Subject subject = services.getSubjectById(Id);
        model.addAttribute("subject", subject);
        return "admin/subjectDetail";
    }


    @PutMapping ("/admin/subject/edit")
    public Subject updateSubject(@RequestBody Integer Id, Subject subject) {
        return services.updateSubject(Id, subject);
    }
}




