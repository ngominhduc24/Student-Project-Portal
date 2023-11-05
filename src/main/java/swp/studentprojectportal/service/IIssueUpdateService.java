package swp.studentprojectportal.service;

import swp.studentprojectportal.model.IssueUpdate;
import java.util.List;
public interface IIssueUpdateService {
    public List<IssueUpdate> findAllByIssue_Id(Integer issueId) ;
}
