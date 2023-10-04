package swp.studentprojectportal.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.service.servicesimpl.SubjectSevice;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SubjectApiController {
    @Autowired
    SubjectSevice subjectSevice;

    @GetMapping("/subject")
    public List<Subject> getSubjectPost(
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "subjectManagerId", defaultValue = "-1") Integer subjectManagerId)
    //@RequestParam(name = "status", defaultValue = "true") Integer status)
    {
        if(pageNo < 0 || pageSize < 0 ) return null;
        return subjectSevice.getSubject(pageNo, pageSize, search.trim(), subjectManagerId);
    }
}
