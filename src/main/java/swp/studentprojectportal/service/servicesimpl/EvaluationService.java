package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Evaluation;
import swp.studentprojectportal.repository.IEvaluationRepository;
import swp.studentprojectportal.service.IEvaluationService;

@Service
public class EvaluationService implements IEvaluationService {
    @Autowired
    IEvaluationRepository evaluationRepository;

    @Override
    public Evaluation getEvaluationBySubmissionId(int submissionId) {
        Evaluation evaluation = evaluationRepository.findEvaluationBySubmissionId(submissionId);
        return evaluation;
    }
}
