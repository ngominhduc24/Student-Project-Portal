package swp.studentprojectportal.controller.subject_manager.subject;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String subjectPage(@RequestParam Integer subjectId,
                              Model model, HttpSession session){
        Subject subject = subjectService.getSubjectById(subjectId);
        model.addAttribute("subject", subject);
        User user = (User) session.getAttribute("user");
        List<Subject> subjectList = subjectService.findAllSubjectByUser(user);
        Page<SubjectSetting> subjectSettingList= subjectSettingService.filter(user.getId(), "", 0, 10, "id", 1, subjectId, -1, -1);
        Page<Assignment> assignmentList = assignmentService.filter(user.getId(),"",0,10,"id",1,subjectId,-1);
        model.addAttribute("pageSize", 10);
        model.addAttribute("pageNo", 0);
        model.addAttribute("subjectId", subjectId);
        model.addAttribute("sortBy", "id");
        model.addAttribute("sortType", 1);
        model.addAttribute("totalPage", assignmentList.getTotalPages());

        model.addAttribute("pageSizeS", 10);
        model.addAttribute("pageNoS", 0);
        model.addAttribute("sortByS", "id");
        model.addAttribute("sortTypeS", 1);
        model.addAttribute("totalPageS", subjectSettingList.getTotalPages());

        model.addAttribute("subjectSettingList", subjectSettingList);
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("assignmentList", assignmentList);
        return "/subject_manager/subjectHome";
    }

    @PostMapping("/subject-manager/subject")
    public String subject(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search, @RequestParam Integer subjectId,
                              @RequestParam(defaultValue = "-1") Integer status,
                              @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "1") Integer sortType,
                              @RequestParam(defaultValue = "0") Integer pageNoS, @RequestParam(defaultValue = "10") Integer pageSizeS,
                              @RequestParam(defaultValue = "") String searchS,
                              @RequestParam(defaultValue = "-1") Integer statusS, @RequestParam(defaultValue = "-1") Integer typeIdS,
                              @RequestParam(defaultValue = "id") String sortByS, @RequestParam(defaultValue = "1") Integer sortTypeS,
                              Model model, HttpSession session){
        Subject subject = subjectService.getSubjectById(subjectId);
        model.addAttribute("subject", subject);
        User user = (User) session.getAttribute("user");
        List<Subject> subjectList = subjectService.findAllSubjectByUser(user);
        Page<SubjectSetting> subjectSettingList= subjectSettingService.filter(user.getId(), searchS, pageNoS, pageSizeS, sortByS, sortTypeS, subjectId, typeIdS, statusS);
        Page<Assignment> assignmentList = assignmentService.filter(user.getId(),search,pageNo,pageSize,sortBy,sortType,subjectId,status);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("subjectId", subjectId);
        model.addAttribute("status", status);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("totalPage", assignmentList.getTotalPages());

        model.addAttribute("pageSizeS", pageSizeS);
        model.addAttribute("pageNoS", pageNoS);
        model.addAttribute("searchS", searchS);
        model.addAttribute("statusS", statusS);
        model.addAttribute("sortByS", sortByS);
        model.addAttribute("typeIdS", typeIdS);
        model.addAttribute("sortTypeS", sortTypeS);
        model.addAttribute("totalPageS", subjectSettingList.getTotalPages());

        model.addAttribute("subjectSettingList", subjectSettingList);
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("assignmentList", assignmentList);
        return "/subject_manager/subjectHome";
    }
}
