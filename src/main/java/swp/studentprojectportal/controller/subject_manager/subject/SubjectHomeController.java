package swp.studentprojectportal.controller.subject_manager.subject;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Assignment;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.SubjectSetting;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.IAssignmentService;
import swp.studentprojectportal.service.ISubjectService;
import swp.studentprojectportal.service.servicesimpl.SubjectSettingService;

import java.util.List;

@Controller
public class SubjectHomeController {
    @Autowired
    ISubjectService subjectService;
    @Autowired
    IAssignmentService assignmentService;
    @Autowired
    SubjectSettingService subjectSettingService;
    @GetMapping("/subject-manager/subject")
    public String subjectPage(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "-1") Integer subjectId,
                              @RequestParam(defaultValue = "-1") Integer status, @RequestParam(defaultValue = "-1") Integer typeId,
                              @RequestParam(defaultValue = "subject_id") String sortBy, @RequestParam(defaultValue = "1") Integer sortType,
                              Model model, HttpSession session){
        Subject subject = subjectService.getSubjectById(subjectId);
        model.addAttribute("subject", subject);
        User user = (User) session.getAttribute("user");
        List<Subject> subjectList = subjectService.findAllSubjectByUser(user);
        Page<SubjectSetting> subjectSettingList= subjectSettingService.filter(user.getId(), search, pageNo, pageSize, sortBy, sortType, subjectId, typeId, status);
        Page<Assignment> assignmentList = assignmentService.filter(user.getId(),search,pageNo,pageSize,sortBy,sortType,subjectId,status);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("subjectId", subjectId);
        model.addAttribute("status", status);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("typeId", typeId);
        model.addAttribute("sortType", sortType);
        model.addAttribute("totalPage", assignmentList.getTotalPages());
        model.addAttribute("totalPage", subjectSettingList.getTotalPages());
        model.addAttribute("subjectSettingList", subjectSettingList);
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("assignmentList", assignmentList);
        return "/subject_manager/subjectHome";
    }
}
