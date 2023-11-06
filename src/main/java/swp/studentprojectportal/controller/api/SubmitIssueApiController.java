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
import swp.studentprojectportal.model.SubjectSetting;
import swp.studentprojectportal.model.SubmitIssue;
import swp.studentprojectportal.service.servicesimpl.*;
import swp.studentprojectportal.utils.Validate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class SubmitIssueApiController {
    @Autowired
    SubmitIssueService submitIssueService;
    @Autowired
    SubjectSettingService subjectSettingService;
    @Autowired
    ClassService classService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/submit-issue")
    public ResponseEntity getSubjectSettingById(@RequestParam(name = "submitIssueId") Integer submitIssueId) {
//        SubmitIssue submitIssue = submitIssueService.findById(submitIssueId);
//        List<Integer> complexityValueList = submitIssueService.findAllComplexitySubjectSettingValueById(submitIssueId);
//        List<Integer> qualityValueList = submitIssueService.findAllQualitySubjectSettingValueById(submitIssueId);
//        for (Integer value : complexityValueList) {
//            System.out.println(value);
//        }
//        for (Integer value : qualityValueList) {
//            System.out.println(value);
        }
//        response.put("details-submit-issue-complexity",List)
//        if(submitIssue != null){
//            Map<String, Object> response = new HashMap<>();
//            response.put("id", submitIssue.getId());
//            response.put("complexity-title", submitIssue.getComplexity().getSettingTitle());
//            response.put("complexity-value", submitIssue.getComplexity().getSettingValue());
//            response.put("quality-title", submitIssue.getQuality().getSettingTitle());
//            response.put("quality-value", submitIssue.getQuality().getSettingValue());
//            response.put("function-loc", submitIssue.getFunctionLoc());
//            return ResponseEntity.ok().body(response);
//        }
//        else
            return ResponseEntity.badRequest().body("Subject Setting not found");

    }



}
