package swp.studentprojectportal.service.servicesimpl;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import swp.studentprojectportal.model.IssueUpdate;
import swp.studentprojectportal.repository.IIssueUpdateRepository;
import swp.studentprojectportal.service.IIssueUpdateService;

import java.util.List;

@Service
public class IssueUpdateService implements IIssueUpdateService {
    @Autowired
    IIssueUpdateRepository issueUpdateRepository;
    @Override
    public List<IssueUpdate> findAllByIssue_Id(Integer issueId){
        return issueUpdateRepository.findAllByIssue_Id(issueId);
    }
}
