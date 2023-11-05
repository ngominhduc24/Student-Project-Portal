package swp.studentprojectportal.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swp.studentprojectportal.model.IssueSetting;
import swp.studentprojectportal.model.IssueUpdate;
import swp.studentprojectportal.model.Milestone;
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.service.servicesimpl.IssueService;
import swp.studentprojectportal.service.servicesimpl.IssueSettingService;
import swp.studentprojectportal.service.servicesimpl.IssueUpdateService;
import swp.studentprojectportal.utils.Validate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class IssueUpdateApiController {
    @Autowired
    IssueService issueService;
    @Autowired
    IssueUpdateService issueUpdateService;

    @GetMapping("/issue-update")
    public ResponseEntity getIssueSettingById(@RequestParam(name = "issueUpdateId") Integer issueUpdateId) {
        IssueUpdate issueUpdate = issueUpdateService.findById(issueUpdateId);
        if(issueUpdate != null){
            Map<String, Object> response = new HashMap<>();
            response.put("id", issueUpdate.getId());
            response.put("title", issueUpdate.getTitle());
            response.put("updateAt",issueUpdate.getUpdateAt());
            response.put("description", issueUpdate.getDescription());
            return ResponseEntity.ok().body(response);
        }
        else
            return ResponseEntity.badRequest().body("Work not found");

    }

    @GetMapping("/update/issue-update")
    public ResponseEntity updateWork(
            @RequestParam(name = "issueUpdateId") Integer issueUpdateId,
            @RequestParam(name = "issueId") int issueId,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description) {

        IssueUpdate issueUpdate = issueUpdateService.findById(issueUpdateId);
        issueUpdate.setTitle(title);
        issueUpdate.setDescription(description);
        issueUpdate.setUpdateAt(Timestamp.valueOf(LocalDateTime.now()));
//        issueUpdate.setUpdateAt(new Timestamp(System.currentTimeMillis()));

        if(Validate.validNotempty(title) == false){
            return ResponseEntity.badRequest().body("Title can not empty. Update failed!");
        }
        else{
            IssueUpdate findIssueUpdate = issueUpdateService.findByIssueAndTitle(issueId,title);
            if(findIssueUpdate!=null && findIssueUpdate.getId() != issueId){
                return ResponseEntity.badRequest().body("Work existed. Update failed!");
            }
            else {
                issueUpdateService.saveIssueUpdate(issueUpdate);
                return ResponseEntity.ok().body("1");

            }
        }

    }

    @GetMapping(path="/add/issue-update")
    public ResponseEntity addNewIssueSetting(@RequestParam(name = "issueId") int issueId,
                                             @RequestParam(name = "title") String title,
                                             @RequestParam(name = "description") String description){
        IssueUpdate issueUpdate = new IssueUpdate();
        issueUpdate.setIssue(issueService.findById(issueId));
        issueUpdate.setTitle(title);
        issueUpdate.setDescription(description);
        System.out.println("title "+title);
        System.out.println("description"+description);

        if(Validate.validNotempty(title) == false){
            return ResponseEntity.badRequest().body("Title can not empty. Add failed!");
        }
        else{
            IssueUpdate findIssueUpdate = issueUpdateService.findByIssueAndTitle(issueId,title);
            if(findIssueUpdate!=null){
                return ResponseEntity.badRequest().body("Wỏk existed. Add failed!");
            }
            else {
                issueUpdateService.saveIssueUpdate(issueUpdate);
                return ResponseEntity.ok().body("1");

            }
        }
    }
}
