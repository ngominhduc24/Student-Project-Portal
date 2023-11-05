package swp.studentprojectportal.service;

import swp.studentprojectportal.model.SubmitIssue;

import java.util.List;

public interface ISubmitIssueService {

    public SubmitIssue insertSubmitIssue(Integer issueId, Integer submissionId);

    public List<SubmitIssue> findAllBySubmissionId(Integer submissionId);

}
