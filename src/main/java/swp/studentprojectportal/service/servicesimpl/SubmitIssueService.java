package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.EvaluationDTO;
import swp.studentprojectportal.model.Issue;
import swp.studentprojectportal.model.SubmitIssue;
import swp.studentprojectportal.repository.IIssueRepository;
import swp.studentprojectportal.repository.ISubmissionRepository;
import swp.studentprojectportal.repository.ISubmitIssueRepository;
import swp.studentprojectportal.service.ISubmitIssueService;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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

    @Override
    public EvaluationDTO setWorkPoint(EvaluationDTO evaluationDTO) {
        List<Issue> issueListOfAssignee =  iIssueRepository.findAllByAssigneeId(evaluationDTO.getStudentId());

        AtomicReference<Integer> workPoint = new AtomicReference<>(0);
        AtomicReference<Float> workGrade = new AtomicReference<>(0f);
        issueListOfAssignee.forEach(issue -> {
            SubmitIssue submitIssue = submitIssueRepository.findSubmitIssueById(issue.getId());
            try {
                if(submitIssue != null && submitIssue.getFunctionLoc() != null && submitIssue.getComplexity() != null) {
                    workPoint.updateAndGet(v -> v + submitIssue.getFunctionLoc());
                    workGrade.updateAndGet(v -> v + submitIssue.getComplexity().getSettingValue() / submitIssue.getFunctionLoc());
                }
            } catch ( Exception e) {
                System.out.println(e.getMessage());
            }
        });

        evaluationDTO.setWorkPoint(workPoint.get());
        evaluationDTO.setWorkGrade(workGrade.get());
        return evaluationDTO;
    }

    @Override
    public SubmitIssue findById(Integer submitIssueId){
        return submitIssueRepository.findById(submitIssueId).orElse(null);
    }

    @Override
    public List<Integer> findAllComplexitySubjectSettingValueById(Integer subjectId){
        return submitIssueRepository.findAllComplexitySubjectSettingValueById(subjectId);
    }

    @Override
    public List<Integer> findAllQualitySubjectSettingValueById(Integer subjectId){
        return submitIssueRepository.findAllQualitySubjectSettingValueById(subjectId);
    }

    @Override
    public int findIdBySubjectIdAndValue(Integer subjectId, Integer settingValue, Integer type){
        return submitIssueRepository.findIdBySubjectIdAndValue(subjectId,settingValue,type);
    }

    @Override
    public SubmitIssue saveSubmitIssue(SubmitIssue submitIssue){
        return submitIssueRepository.save(submitIssue);
    }
}
