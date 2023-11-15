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
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.service.servicesimpl.IssueSettingService;
import swp.studentprojectportal.service.servicesimpl.SubjectService;
import swp.studentprojectportal.service.servicesimpl.SubjectSettingService;
import swp.studentprojectportal.utils.Validate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class SubjectSettingApiController {
    @Autowired
    SubjectSettingService subjectSettingService;
    @Autowired
    ClassService classService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/subject-setting")
    public ResponseEntity getSubjectSettingById(@RequestParam(name = "subjectSettingId") Integer subjectSettingId) {
        SubjectSetting subjectSetting = subjectSettingService.findById(subjectSettingId);
        // create response entity with milestone object
        if(subjectSetting != null){
            Map<String, Object> response = new HashMap<>();
            response.put("id", subjectSetting.getId());
            response.put("title", subjectSetting.getSettingTitle());
            response.put("value", subjectSetting.getSettingValue());
            response.put("displayOrder", subjectSetting.getDisplayOrder());
            response.put("typeId", subjectSetting.getTypeId());
            response.put("status", subjectSetting.isStatus() == true ? "1" : "0");
            return ResponseEntity.ok().body(response);
        }
        else
            return ResponseEntity.badRequest().body("Subject Setting not found");

    }

    @GetMapping("/update/subject-setting")
    public ResponseEntity updateSubjectSetting(
            @RequestParam(name = "subjectSettingId") Integer subjectSettingId,
            @RequestParam(name = "subjectId") int subjectId,
            @RequestParam(name = "typeId") String typeId,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "value") String value,
            @RequestParam(name = "displayOrder") String displayOrder,
            @RequestParam(name = "status") Integer status) {

        SubjectSetting subjectSetting = subjectSettingService.findById(subjectSettingId);
        //ubjectSetting.setSubject(subjectService.findSubjectById(subjectId));
        subjectSetting.setSettingTitle(title);
        subjectSetting.setStatus(status == 1 ? true : false);
        if(!Validate.validNotempty(title))
            return ResponseEntity.badRequest().body("Title can not empty. Update failed!");
        else{
            if(!Validate.validNotempty(value))
                return ResponseEntity.badRequest().body("Value can not empty. Update failed!");
            else{
                if(!Validate.validNotempty(displayOrder))
                    return ResponseEntity.badRequest().body("DisplayOrder can not empty. Update failed!");
                else{
                    if(!Validate.isNumeric(value))
                        return ResponseEntity.badRequest().body("Value must be positive number. Update failed!");
                    else{
                        if(!Validate.isNumeric(displayOrder))
                            return ResponseEntity.badRequest().body("DisplayOrder must be positive number. Update failed!");
                        else{
                            Integer value_real = Integer.parseInt(value);
                            Integer typeId_real = Integer.parseInt(typeId);
                            Integer displayOrder_real = Integer.parseInt(displayOrder);
                            subjectSetting.setSettingValue(value_real);
                            subjectSetting.setTypeId(typeId_real);
                            subjectSetting.setDisplayOrder(displayOrder_real);
                            if(value_real > 100 && typeId_real == 2)
                                return ResponseEntity.badRequest().body("Value must be in range [0,100]. Update failed!");
                            else {
                                SubjectSetting findSubjectSetting = subjectSettingService.findByIdAndTypeIdAndTitle(subjectId, typeId_real, title);
                                if (findSubjectSetting != null && findSubjectSetting.getId() != subjectSettingId) {
                                    return ResponseEntity.badRequest().body("Subject setting existed. Update failed!");
                                } else {
                                    subjectSettingService.saveSubjectSetting(subjectSetting);
                                    return ResponseEntity.ok().body("1");
                                }
                            }
                        }
                    }
                }
            }

        }


    }

    @GetMapping(path="/add/subject-setting")
    public ResponseEntity addSubjectSetting(@RequestParam(name = "subjectId") int subjectId,
                                                     @RequestParam(name = "typeId") String typeId,
                                                     @RequestParam(name = "title") String title,
                                                     @RequestParam(name = "value") String value,
                                                     @RequestParam(name = "displayOrder") String displayOrder){
        SubjectSetting subjectSetting = new SubjectSetting();
        subjectSetting.setSubject(subjectService.findSubjectById(subjectId));
        subjectSetting.setSettingTitle(title);


        if(!Validate.validNotempty(title))
            return ResponseEntity.badRequest().body("Title can not empty. Add failed!");
        else{
            if(!Validate.validNotempty(value))
                return ResponseEntity.badRequest().body("Value can not empty. Add failed!");
            else{
                if(!Validate.validNotempty(displayOrder))
                    return ResponseEntity.badRequest().body("DisplayOrder can not empty. Add failed!");
                else{
                    if(!Validate.isNumeric(value))
                        return ResponseEntity.badRequest().body("Value must be positive number. Add failed!");
                    else{
                        if(!Validate.isNumeric(displayOrder))
                            return ResponseEntity.badRequest().body("DisplayOrder must be positive number. Add failed!");
                        else{
                            Integer value_real = Integer.parseInt(value);
                            Integer typeId_real = Integer.parseInt(typeId);
                            Integer displayOrder_real = Integer.parseInt(displayOrder);
                            subjectSetting.setSettingValue(value_real);
                            subjectSetting.setTypeId(typeId_real);
                            subjectSetting.setDisplayOrder(displayOrder_real);
                            subjectSetting.setStatus(true);
                            if(value_real > 100 && typeId_real == 2)
                                return ResponseEntity.badRequest().body("Value must be in range [0,100]. Add failed!");
                            else {
                                SubjectSetting findSubjectSetting = subjectSettingService.findByIdAndTypeIdAndTitle(subjectId, typeId_real, title);
                                if (findSubjectSetting != null) {
                                    return ResponseEntity.badRequest().body("Subject setting existed. Add failed!");
                                } else {
                                    subjectSettingService.saveSubjectSetting(subjectSetting);
                                    return ResponseEntity.ok().body("1");
                                }
                            }
                        }
                    }
                }
            }

        }


    }

}
