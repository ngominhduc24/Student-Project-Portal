package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Issue;
import swp.studentprojectportal.repository.IIssueRepository;
import swp.studentprojectportal.service.IIssueService;

import java.util.List;

@Service
public class IssueService implements IIssueService {
    @Autowired
    IIssueRepository issueRepository;

    @Override
    public List<Issue> getAllIssueByStudentId(Integer studentId){
        return issueRepository.findAllByAssigneeId(studentId);
    }
}
