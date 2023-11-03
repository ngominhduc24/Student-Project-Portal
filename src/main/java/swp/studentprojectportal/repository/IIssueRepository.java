package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Issue;

import java.util.List;

@Repository
public interface IIssueRepository extends JpaRepository<Issue, Integer> {
    List<Issue> findAllByAssigneeId(Integer studentId);

    public List<Issue> findAllByMilestoneId(Integer milestoneId);
}
