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
    public ResponseEntity getSubmitIssueById(@RequestParam(name = "submitIssueId") Integer submitIssueId) {
        SubmitIssue submitIssue = submitIssueService.findById(submitIssueId);
        if(submitIssue != null){
            Map<String, Object> response = new HashMap<>();
            response.put("id", submitIssue.getId());
            if(submitIssue.getComplexity()==null) response.put("complexityValue", -1);
            else response.put("complexityValue", submitIssue.getComplexity().getSettingValue());
            if(submitIssue.getQuality()==null) response.put("qualityValue", -1);
            else response.put("qualityValue", submitIssue.getQuality().getSettingValue());
            response.put("functionLoc", submitIssue.getFunctionLoc());
            return ResponseEntity.ok().body(response);
        }
        else
            return ResponseEntity.badRequest().body("Subject Setting not found");

    }

    @GetMapping(path="/update/submit-issue")
    public ResponseEntity updateSubmitIssue(@RequestParam(name = "submitIssueId") Integer submitIssueId,
                                            @RequestParam(name = "functionLoc") String fuctionLoc,
                                            @RequestParam(name = "complexity") Integer complexity,
                                            @RequestParam(name = "quality") Integer quality,
                                            @RequestParam(name = "subjectId") int subjectId){
        System.out.println("fetc du lieu nay bạn ơi ???");
        SubmitIssue submitIssue = submitIssueService.findById(submitIssueId);
        int complexityId = submitIssueService.findIdBySubjectIdAndValue(subjectId,complexity,1);
        int qualityId = submitIssueService.findIdBySubjectIdAndValue(subjectId,quality,2);
        submitIssue.setComplexity(subjectSettingService.findById(complexityId));
        submitIssue.setQuality(subjectSettingService.findById(qualityId));
        if(!Validate.validNotempty(fuctionLoc)){
            return ResponseEntity.badRequest().body("Function Loc can not empty. Evalute failed!");
        }
        else{
            if(!Validate.isNumeric(fuctionLoc)){
                return ResponseEntity.badRequest().body("Function Loc must be positive integer. Evalute failed!");
            }
            else{
                Integer real_loc = Integer.parseInt(fuctionLoc);
                submitIssue.setFunctionLoc(real_loc);
                submitIssueService.saveSubmitIssue(submitIssue);
                return ResponseEntity.ok().body("1");
            }
        }
    }
}
