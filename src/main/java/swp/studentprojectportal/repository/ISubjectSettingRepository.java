package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Setting;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.SubjectSetting;

import java.util.List;
@Repository
public interface ISubjectSettingRepository extends JpaRepository<SubjectSetting, Integer> {
    List<SubjectSetting> findSubjectSettingBySubjectAndTypeIdOrderByDisplayOrder(Subject subject, Integer typeId);
//    @Query(value = "SELECT *\n" +
//            "FROM swp391.subject_setting \n" +
//            "WHERE subject_id=1 and type_id=1 and status =1\n" +
//            "ORDER BY subject_id, type_id, display_order", nativeQuery = true)
//    List<SubjectSetting> searchSubjectSettingBySubjectAndTypeIdAndStatus(Integer subjectId, Integer typeId, Integer status);
}
