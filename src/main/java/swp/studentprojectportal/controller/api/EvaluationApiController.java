package swp.studentprojectportal.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swp.studentprojectportal.model.Evaluation;
import swp.studentprojectportal.service.servicesimpl.EvaluationService;

@RestController
@RequestMapping("/api/v1")
public class EvaluationApiController {
    @Autowired
    EvaluationService evaluationService;

    @GetMapping("/evaluation/update")
    public ResponseEntity updateEvaluationComment(
            @RequestParam(name = "evaluationId") Integer evaluationId,
            @RequestParam(name = "comment") String comment) {
        if(evaluationService.updateComment(evaluationId, comment)) {
            return ResponseEntity.ok().body("Update comment success");
        } else {
            return ResponseEntity.badRequest().body("Update comment failed");
        }
    }
}
