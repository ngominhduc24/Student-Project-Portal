package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Evaluation;

import java.util.List;

@Repository
public interface IEvaluationRepository extends JpaRepository<Evaluation, Integer> {
    List<Evaluation> findEvaluationBySubmissionId(Integer submissionId);
}
