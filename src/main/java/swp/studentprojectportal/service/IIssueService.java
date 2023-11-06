package swp.studentprojectportal.service;

import swp.studentprojectportal.model.Issue;

import java.util.List;

public interface IIssueService {

    Issue getIssueById(Integer issueId);

    Issue saveIssue(Issue issue);

    List<Issue> getAllIssueByStudentId(Integer studentId);

    List<Issue> filterIssue(List<Issue> listIssue, Integer projectId, Integer milestoneId, Integer assigneeId, String search);

    public List<Issue> findAllByMilestoneId(Integer milestoneId);

    public boolean updateIssueAssignee(Integer issueId, Integer assigneeId);

    public Issue findById(int id);
}
