package swp.studentprojectportal.service;

import org.springframework.data.domain.Page;
import swp.studentprojectportal.model.IssueUpdate;
import java.util.List;
public interface IIssueUpdateService{
    public List<IssueUpdate> findAllByIssue_Id(Integer issueId) ;

    public IssueUpdate findById(int id);

    public IssueUpdate findByIssueAndTitle(int issueId,String title);

    public IssueUpdate saveIssueUpdate(IssueUpdate issueUpdate);

    Page<IssueUpdate> filterWork(Integer issueId, String search, Integer pageNo, Integer pageSize,
                                               String sortBy, Integer sortType);
}
