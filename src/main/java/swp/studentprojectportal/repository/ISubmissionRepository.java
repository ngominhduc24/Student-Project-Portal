package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Submission;

@Repository
public interface ISubmissionRepository extends JpaRepository<Submission, Integer> {



}
