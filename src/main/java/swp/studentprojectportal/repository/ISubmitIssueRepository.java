package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.SubmitIssue;

import java.util.List;

@Repository
public interface ISubmitIssueRepository extends JpaRepository<SubmitIssue, Integer> {

    public List<SubmitIssue> findAllBySubmitId(Integer submissionId);

    public SubmitIssue findSubmitIssueById(Integer issueId);

    @Query(value="SELECT setting_value FROM subject_setting WHERE subject_id = :subjectId AND type_id=1",
            nativeQuery = true)
    public List<Integer> findAllComplexitySubjectSettingValueById(
            @Param("subjectId") Integer subjectId);

    @Query(value="SELECT setting_value FROM subject_setting WHERE subject_id = :subjectId AND type_id=2",
            nativeQuery = true)
    public List<Integer> findAllQualitySubjectSettingValueById(
            @Param("subjectId") Integer subjectId);

}
