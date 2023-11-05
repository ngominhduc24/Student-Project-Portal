package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Evaluation;

@Repository
public interface IEvaluationRepository extends JpaRepository<Evaluation, Integer> {
    Evaluation findEvaluationBySubmissionId(Integer submissionId);
}
