package swp.studentprojectportal.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.IStudentClassRepository;
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.service.servicesimpl.MilestoneService;
import swp.studentprojectportal.service.servicesimpl.StudentClassService;
import swp.studentprojectportal.service.servicesimpl.UserService;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MileStoneApiController {
        @Autowired
        MilestoneService milestoneService;


        @GetMapping("/newMilestone")
        public ResponseEntity addNewMilestone(
                @RequestParam(name = "classId") Integer classId,
                @RequestParam(name = "subjectId") String subjectCode,
                @RequestParam(name = "title") String title,
                @RequestParam(name = "description") String description,
                @RequestParam(name = "startdate") LocalDateTime startDate,
                @RequestParam(name = "enddate") LocalDateTime endDate,
                @RequestParam(name = "status", defaultValue = "1") Integer status) {
            boolean result =  milestoneService.addNewMilestone(classId, subjectCode, title, description, startDate, endDate, status);
            if(result)
                return ResponseEntity.ok().body("Add new milestone successfully");
            else
                return ResponseEntity.badRequest().body("Add new milestone failed");
        }
}
