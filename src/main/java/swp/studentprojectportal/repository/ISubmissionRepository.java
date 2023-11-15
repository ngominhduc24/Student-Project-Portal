package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Submission;

import java.util.List;

@Repository
public interface ISubmissionRepository extends JpaRepository<Submission, Integer> {

    public List<Submission> findAllByProjectId(Integer projectId);

}
