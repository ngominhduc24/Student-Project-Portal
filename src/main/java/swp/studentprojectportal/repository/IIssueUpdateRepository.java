package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swp.studentprojectportal.model.IssueUpdate;

import java.util.List;

public interface IIssueUpdateRepository extends JpaRepository<IssueUpdate, Integer> {
    public List<IssueUpdate> findAllByIssue_Id(Integer issueId);
}
