package swp.studentprojectportal.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.service.servicesimpl.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SubjectApiController {
    @Autowired
    SubjectService subjectService;

    @GetMapping("/subject")
    public List<Subject> getSubjectPost(
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "subjectManagerId", defaultValue = "-1") Integer subjectManagerId,
            @RequestParam(name = "status", defaultValue = "-1") Integer status)
    {
        if(pageNo < 0 || pageSize < 0 ) return null;
        return subjectService.getSubject(pageNo, pageSize, search, subjectManagerId, status);
    }
}
