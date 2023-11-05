package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Evaluation;
import swp.studentprojectportal.repository.IEvaluationRepository;
import swp.studentprojectportal.service.IEvaluationService;

import java.util.List;

@Service
public class EvaluationService implements IEvaluationService {
    @Autowired
    IEvaluationRepository evaluationRepository;

    @Override
    public List<Evaluation> getEvaluationBySubmissionId(int submissionId) {
        return evaluationRepository.findEvaluationBySubmissionId(submissionId);
    }
}
