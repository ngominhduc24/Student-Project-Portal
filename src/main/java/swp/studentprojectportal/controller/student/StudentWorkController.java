package swp.studentprojectportal.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.IssueUpdate;
import swp.studentprojectportal.service.servicesimpl.IssueUpdateService;

@Controller
public class StudentWorkController {
    @Autowired
    IssueUpdateService issueUpdateService;
    @GetMapping("student/work/{issueId}")
    public String WorkList(@PathVariable Integer issueId,@RequestParam(defaultValue = "0") Integer pageNo,
                           @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String search,
                           @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "1") Integer sortType,
                           Model model){
        Page<IssueUpdate> workList = issueUpdateService.filterWork(issueId, search, pageNo, pageSize, sortBy, sortType);
        model.addAttribute("issueId",issueId);
        model.addAttribute("workList",workList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("totalPage", workList.getTotalPages());
        return "student/workList";
    }
    @GetMapping(path = "student/work/delete")
    public String DeleteWork(@RequestParam int id, Model model){
        IssueUpdate issueUpdate = issueUpdateService.findById(id);
        if( issueUpdateService.deleteById(id)) {
            //Delete
            int issueId = issueUpdate.getIssue().getId();
            return "redirect:/student/work/" + issueId;
        }
        return "redirect:/student/home";
    }
}
