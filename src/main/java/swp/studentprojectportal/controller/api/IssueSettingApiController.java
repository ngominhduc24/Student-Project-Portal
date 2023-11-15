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
import swp.studentprojectportal.model.Milestone;
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.service.servicesimpl.IssueSettingService;
import swp.studentprojectportal.service.servicesimpl.SubjectService;
import swp.studentprojectportal.utils.Validate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class IssueSettingApiController {
    @Autowired
    ClassService classService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    IssueSettingService issueSettingService;
    @Autowired
    ObjectMapper objectMapper;
    @GetMapping("/issue-setting")
    public ResponseEntity getIssueSettingById(@RequestParam(name = "issueSettingId") Integer issueSettingId) {
        IssueSetting issueSetting = issueSettingService.findById(issueSettingId);
        // create response entity with milestone object
        if(issueSetting != null){
            Map<String, Object> response = new HashMap<>();
            response.put("id", issueSetting.getId());
            response.put("title", issueSetting.getSettingTitle());
            response.put("group",issueSetting.getSettingGroup());
            response.put("description", issueSetting.getDescription());
            response.put("status", issueSetting.isStatus() == true ? "1" : "0");
            response.put("subjectId", issueSetting.getSubject() == null ? "" : issueSetting.getSubject().getId());
            response.put("classId", issueSetting.getAclass() == null ? "" : issueSetting.getAclass().getId());
            response.put("projectId", issueSetting.getProject() == null ? "" : issueSetting.getProject().getId());
            return ResponseEntity.ok().body(response);
        }
        else
            return ResponseEntity.badRequest().body("Issue Setting not found");

    }
    @GetMapping("/update/issue-setting")
    public ResponseEntity updateIssueSettingForClass(
            @RequestParam(name = "issueSettingId") Integer issueSettingId,
            @RequestParam(name = "classId") int classId,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "group") String group,
            @RequestParam(name = "status") Integer status) {

        IssueSetting issueSetting = issueSettingService.findById(issueSettingId);
        issueSetting.setDescription(description);
        issueSetting.setSettingTitle(title);
        issueSetting.setSettingGroup(group);
        issueSetting.setStatus(status == 1 ? true : false);
        if(Validate.validNotempty(group) == false){
            return ResponseEntity.badRequest().body("Group can not empty. Update failed!");
        }
        else{
            IssueSetting findIssueSetting = issueSettingService.findByClassAndGroupAndTitle(classId,group,title);
            if(findIssueSetting!=null && findIssueSetting.getId() != issueSettingId){
                return ResponseEntity.badRequest().body("Issue setting existed. Update failed!");
            }
            else {
                issueSettingService.saveIssueSetting(issueSetting);
                return ResponseEntity.ok().body("1");

            }
        }

    }
    @GetMapping("/update2/issue-setting")
    public ResponseEntity updateIssueSettingForSubject(
            @RequestParam(name = "issueSettingId") Integer issueSettingId,
            @RequestParam(name = "subjectId") int subjectId,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "group") String group,
            @RequestParam(name = "status") Integer status) {

        IssueSetting issueSetting = issueSettingService.findById(issueSettingId);
        issueSetting.setDescription(description);
        issueSetting.setSettingTitle(title);
        issueSetting.setSettingGroup(group);
        issueSetting.setStatus(status == 1 ? true : false);
        if(Validate.validNotempty(group) == false){
            return ResponseEntity.badRequest().body("Group can not empty. Update failed!");
        }
        else{
            IssueSetting findIssueSetting = issueSettingService.findBySubjectAndGroupAndTitle(subjectId,group,title);
            if(findIssueSetting!=null && findIssueSetting.getId() != issueSettingId){
                return ResponseEntity.badRequest().body("Issue setting existed. Update failed!");
            }
            else {
                issueSettingService.saveIssueSetting(issueSetting);
                return ResponseEntity.ok().body("1");

            }
        }

    }


    @GetMapping(path="/add/issue-setting")
    public ResponseEntity addNewIssueSettingForClass(@RequestParam(name = "classId") int classId,
                                             @RequestParam(name = "title") String title,
                                             @RequestParam(name = "description") String description,
                                             @RequestParam(name = "group") String group){
        IssueSetting issueSetting = new IssueSetting();
        issueSetting.setAclass(classService.findById(classId));
        issueSetting.setSettingGroup(group);
        issueSetting.setSettingTitle(title);
        issueSetting.setDescription(description);
        System.out.println("group "+group);
        System.out.println("title "+title);
        System.out.println("description"+description);
        issueSetting.setStatus(true);

        if(Validate.validNotempty(group) == false){
            return ResponseEntity.badRequest().body("Group can not empty. Add failed!");
        }
        else{
            IssueSetting findIssueSetting = issueSettingService.findByClassAndGroupAndTitle(classId,group,title);
            if(findIssueSetting!=null){
                return ResponseEntity.badRequest().body("Issue setting existed. Add failed!");
            }
            else {
                issueSettingService.saveIssueSetting(issueSetting);
                return ResponseEntity.ok().body("1");

            }
        }
    }

    @GetMapping(path="/add2/issue-setting")
    public ResponseEntity addNewIssueSettingForSubject(@RequestParam(name = "subjectId") int subjectId,
                                             @RequestParam(name = "title") String title,
                                             @RequestParam(name = "description") String description,
                                             @RequestParam(name = "group") String group){
        IssueSetting issueSetting = new IssueSetting();
        issueSetting.setSubject(subjectService.findSubjectById(subjectId));
        issueSetting.setSettingGroup(group);
        issueSetting.setSettingTitle(title);
        issueSetting.setDescription(description);
        System.out.println("group "+group);
        System.out.println("title "+title);
        System.out.println("description"+description);
        issueSetting.setStatus(true);

        if(Validate.validNotempty(group) == false){
            return ResponseEntity.badRequest().body("Group can not empty. Add failed!");
        }
        else{
            IssueSetting findIssueSetting = issueSettingService.findBySubjectAndGroupAndTitle(subjectId,group,title);
            if(findIssueSetting!=null){
                return ResponseEntity.badRequest().body("Issue setting existed. Add failed!");
            }
            else {
                issueSettingService.saveIssueSetting(issueSetting);
                return ResponseEntity.ok().body("1");

            }
        }
    }
}
