package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.*;
import swp.studentprojectportal.repository.IEvaluationRepository;
import swp.studentprojectportal.service.IEvaluationService;

import java.util.List;

@Service
public class EvaluationService implements IEvaluationService {
    @Autowired
    IEvaluationRepository evaluationRepository;

    @Autowired
    SubmissionService submissionService;

    @Autowired
    StudentClassService studentClassService;

    @Autowired
    CriteriaService criteriaService;

    @Autowired
    AssignmentService assignmentService;

    @Override
    public List<Evaluation> getEvaluationBySubmissionId(int submissionId) {
        return evaluationRepository.findEvaluationBySubmissionId(submissionId);
    }

    @Override
    public boolean updateEvaluation(Integer criteriaGradeId, Float grade) {
        Evaluation evaluation = evaluationRepository.findById(criteriaGradeId).orElse(null);
        if(evaluation == null)
            return false;
        evaluation.setGrade(grade);
        evaluationRepository.save(evaluation);
        return true;
    }

    public List<Evaluation> createEvaluation(int submissionId) {
        Submission submission =  submissionService.findById(submissionId);

        // Get all student of project
        List<StudentClass> studentList = studentClassService.findAllByProjectId(submission.getProject().getId());

        // get all criteria
        System.out.println("123");
        List<Criteria> criteriaList = criteriaService.getAllCriteriaByAssignmentId(submission.getMilestone().getSubjectAssignment().getId());
        criteriaList.forEach( e -> {
            System.out.println(e.getName());
                }
        );

        return null;
    }
}
