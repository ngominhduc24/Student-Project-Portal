package swp.studentprojectportal.controller.subject_manager.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Criteria;
import swp.studentprojectportal.model.Setting;
import swp.studentprojectportal.service.servicesimpl.CriteriaService;
import swp.studentprojectportal.service.servicesimpl.SubjectService;
@Controller
public class SubjectCriteriaController {
    @Autowired
    CriteriaService criteriaService;
    @GetMapping("/subject-manager/subject/criteria")
    public String settingPage(@RequestParam("id") Integer assignmentId,
                              @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "-1") Integer typeId,
                              @RequestParam(defaultValue = "-1") Integer status, @RequestParam(defaultValue = "id") String sortBy,
                              @RequestParam(defaultValue = "1") Integer sortType, Model model) {
        Page<Criteria> criteriaList = criteriaService.filter(assignmentId, search, pageNo, pageSize, sortBy, sortType, status);
        model.addAttribute("assignmentId", assignmentId);
        model.addAttribute("criteriaList", criteriaList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("status", status);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("totalPage", criteriaList.getTotalPages());
        return "subject_manager/criteriaList";
    }

    @GetMapping("/subject-manager/subject/criteria/updateStatus")
    public String updateSettingStatus(
            @RequestParam int id,
            @RequestParam boolean status) {
        Criteria criteria = criteriaService.findById(id);
        criteria.setStatus(status);
        criteriaService.save(criteria);
        return "redirect:/";
    }
}
