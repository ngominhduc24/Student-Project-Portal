package swp.studentprojectportal.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swp.studentprojectportal.model.Milestone;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.IStudentClassRepository;
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.service.servicesimpl.MilestoneService;
import swp.studentprojectportal.service.servicesimpl.StudentClassService;
import swp.studentprojectportal.service.servicesimpl.UserService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class MileStoneApiController {
        @Autowired
        MilestoneService milestoneService;
        @Autowired
        ObjectMapper objectMapper;

        @GetMapping("/newMilestone")
        public ResponseEntity addNewMilestone(
                @RequestParam(name = "classId") Integer classId,
                @RequestParam(name = "title") String title,
                @RequestParam(name = "description") String description,
                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                @RequestParam(name = "status", defaultValue = "1") Integer status) {
            boolean result =  milestoneService.addNewMilestone(classId, title, description, startDate, endDate, status);
            if(result)
                return ResponseEntity.ok().body("Add new milestone successfully");
            else
                return ResponseEntity.badRequest().body("Add new milestone failed");
        }

        @GetMapping("/milestone")
        public ResponseEntity getMilestoneByClassId(@RequestParam(name = "milestoneId") Integer milestoneId) {
            Milestone milestone = milestoneService.findMilestoneById(milestoneId);
            // create response entity with milestone object
            if(milestone != null){
                Map<String, Object> response = new HashMap<>();
                response.put("title", milestone.getTitle());
                response.put("description", milestone.getDescription());
                response.put("startDate", milestone.getStartDate().toString());
                response.put("endDate", milestone.getEndDate().toString());
                response.put("status", milestone.isStatus() == true ? "1" : "0");
                response.put("classId", milestone.getAclass() == null ? "" : milestone.getAclass().getId());
                response.put("projectId", milestone.getProject() == null ? "" : milestone.getProject().getId());
                return ResponseEntity.ok().body(response);
            }
            else
                return ResponseEntity.badRequest().body("Milestone not found");

        }
}
