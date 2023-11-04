package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.SubmitIssue;
import swp.studentprojectportal.repository.IIssueRepository;
import swp.studentprojectportal.repository.ISubmissionRepository;
import swp.studentprojectportal.repository.ISubmitIssueRepository;
import swp.studentprojectportal.service.IIssueService;
import swp.studentprojectportal.service.ISubmitIssueService;

import java.util.List;

@Service
public class SubmitIssueService implements ISubmitIssueService {
    @Autowired
    ISubmitIssueRepository submitIssueRepository;
    @Autowired
    IIssueRepository iIssueRepository;
    @Autowired
    ISubmissionRepository submissionRepository;

    @Override
    public SubmitIssue insertSubmitIssue(Integer issueId, Integer submissionId) {
        SubmitIssue submitIssue = new SubmitIssue();

        submitIssue.setIssue(iIssueRepository.findById(issueId).get());
        submitIssue.setSubmit(submissionRepository.findById(submissionId).get());

        submitIssueRepository.save(submitIssue);

        return submitIssue;
    }

    @Override
    public List<SubmitIssue> findAllBySubmissionId(Integer submissionId) {
        return submitIssueRepository.findAllBySubmitId(submissionId);
    }
}
