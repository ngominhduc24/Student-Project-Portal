package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.SubmissionPersonal;

@Repository
public interface ISubmissionPersonalReposiroty extends JpaRepository<SubmissionPersonal, Integer> {
}
