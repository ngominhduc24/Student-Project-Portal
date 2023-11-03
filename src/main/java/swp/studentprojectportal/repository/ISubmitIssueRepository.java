package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.SubmitIssue;

@Repository
public interface ISubmitIssueRepository extends JpaRepository<SubmitIssue, Integer> {



}
