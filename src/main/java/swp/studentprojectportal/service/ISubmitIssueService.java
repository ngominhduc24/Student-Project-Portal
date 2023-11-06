package swp.studentprojectportal.service;

import swp.studentprojectportal.model.EvaluationDTO;
import swp.studentprojectportal.model.SubmitIssue;

import java.util.List;

public interface ISubmitIssueService {

    public SubmitIssue insertSubmitIssue(Integer issueId, Integer submissionId);

    public List<SubmitIssue> findAllBySubmissionId(Integer submissionId);

    EvaluationDTO setWorkPoint(EvaluationDTO evaluationDTO);

    public SubmitIssue findById(Integer submitIssueId);

    public List<Integer> findAllComplexitySubjectSettingValueById(Integer subjectId);

    public List<Integer> findAllQualitySubjectSettingValueById(Integer subjectId);
}
