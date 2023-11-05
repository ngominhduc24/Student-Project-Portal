package swp.studentprojectportal.service;

import swp.studentprojectportal.model.Evaluation;

public interface IEvaluationService {
    Evaluation getEvaluationBySubmissionId(int submissionId);
}
