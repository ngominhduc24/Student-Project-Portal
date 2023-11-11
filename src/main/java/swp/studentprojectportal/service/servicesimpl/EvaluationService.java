package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.*;
import swp.studentprojectportal.repository.IEvaluationRepository;
import swp.studentprojectportal.service.IEvaluationService;

import java.util.ArrayList;
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
    public Float updateEvaluation(Integer criteriaGradeId, Float grade) {
        Evaluation evaluation = evaluationRepository.findById(criteriaGradeId).orElse(null);
        if(evaluation == null)
            return -1f;
        evaluation.setGrade(grade);
        evaluationRepository.save(evaluation);
        return evaluation.getGrade()*evaluation.getWeight()/100;
    }

    @Override
    public boolean updateComment(Integer evaluationId, String comment) {
        try {
            Evaluation evaluation = evaluationRepository.findById(evaluationId).get();
            evaluation.setComment(comment);
            evaluationRepository.save(evaluation);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public Evaluation getEvaluationById(Integer evaluationId) {
        return evaluationRepository.findEvaluationById(evaluationId);
    }

    public List<Evaluation> createEvaluation(int submissionId) {
        List<Evaluation> output = new ArrayList<>();
        Submission submission =  submissionService.findById(submissionId);

        // Get all student of project
        List<StudentClass> studentList = studentClassService.findAllByProjectId(submission.getProject().getId());

        // get all criteria
        List<Criteria> criteriaList = criteriaService.getAllCriteriaByAssignmentId(submission.getMilestone().getSubjectAssignment().getId());
        criteriaList.forEach( criteria -> {

            studentList.forEach(student -> {
                try {
                    Evaluation evaluation = new Evaluation();
                    evaluation.setCriteria(criteria.getName());
                    evaluation.setWeight(criteria.getWeight());
                    evaluation.setSubmission(submission);
                    evaluation.setStudent(student.getStudent());
                    evaluation.setGrade(0f);
                    evaluationRepository.save(evaluation);
                    output.add(evaluation);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        return output;
    }
}
