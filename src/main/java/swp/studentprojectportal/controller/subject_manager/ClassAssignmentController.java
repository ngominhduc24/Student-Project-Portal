package swp.studentprojectportal.controller.subject_manager;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Milestone;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.service.IClassService;
import swp.studentprojectportal.service.IMilestoneService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class ClassAssignmentController {
    @Autowired
    IMilestoneService milestoneService;
    @Autowired
    IClassService classService;

    @GetMapping("/subject-manager/addClassAssignment")
    public String AddSubjectAssignment(Model model, @RequestParam("id") Integer classId){
        Class Class = classService.getClass(classId);
        model.addAttribute("class", Class);
        return "subject_manager/class_assignment/classAssignmentAdd";
    }

    @GetMapping("/subject-manager/classAssignmentDetail")
    public String subjectAssignmentDetail(Model model, @RequestParam("id") Integer id){
        Milestone milestone = milestoneService.findMilestoneById(id);
        model.addAttribute("milestone", milestone);
        return "subject_manager/class_assignment/classAssignmentDetails";
    }
}
