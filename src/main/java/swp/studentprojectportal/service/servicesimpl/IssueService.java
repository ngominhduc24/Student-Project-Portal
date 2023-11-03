package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Issue;
import swp.studentprojectportal.repository.IIssueRepository;
import swp.studentprojectportal.repository.IUserRepository;
import swp.studentprojectportal.service.IIssueService;

import java.util.List;

@Service
public class IssueService implements IIssueService {
    @Autowired
    IIssueRepository issueRepository;
    @Autowired
    IUserRepository userRepository;

    @Override
    public List<Issue> getAllIssueByStudentId(Integer studentId){
        return issueRepository.findAllByAssigneeId(studentId);
    }

    @Override
    public List<Issue> findAllByMilestoneId(Integer milestoneId) {
        return issueRepository.findAllByMilestoneId(milestoneId);
    }

    @Override
    public boolean updateIssueAssignee(Integer issueId, Integer assigneeId) {
        try {
            Issue issue = issueRepository.findById(issueId).get();

            issue.setAssignee(userRepository.findUserById(assigneeId));

            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
