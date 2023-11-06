package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.SubmitIssue;

import java.util.List;

@Repository
public interface ISubmitIssueRepository extends JpaRepository<SubmitIssue, Integer> {

    public List<SubmitIssue> findAllBySubmitId(Integer submissionId);

    public SubmitIssue findSubmitIssueById(Integer issueId);

}
