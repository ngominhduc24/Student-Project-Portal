package swp.studentprojectportal.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swp.studentprojectportal.model.IssueSetting;
import swp.studentprojectportal.model.Milestone;
import swp.studentprojectportal.service.servicesimpl.IssueSettingService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class IssueSettingApiController {
    @Autowired
    IssueSettingService issueSettingService;
    @Autowired
    ObjectMapper objectMapper;
    @GetMapping("/issue-setting")
    public ResponseEntity getMilestoneByClassId(@RequestParam(name = "issueSettingId") Integer issueSettingId) {
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
}
