package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.SubmissionPersonal;

import java.util.List;

@Repository
public interface ISubmissionPersonalReposiroty extends JpaRepository<SubmissionPersonal, Integer> {
    List<SubmissionPersonal> findAllBySubmissionId(int submissionId);

    SubmissionPersonal findBySubmissionIdAndStudentId(int submissionId, int studentId);
}
