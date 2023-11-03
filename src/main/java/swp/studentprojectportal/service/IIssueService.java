package swp.studentprojectportal.service;

import swp.studentprojectportal.model.Issue;

import java.util.List;

public interface IIssueService {
    List<Issue> getAllIssueByStudentId(Integer studentId);
}
