package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.SubjectSetting;

import java.util.List;
@Repository
public interface ISubjectSettingRepository extends JpaRepository<SubjectSetting, Integer> {
    @Query(value="SELECT ss.* FROM subject_setting ss join subject s on ss.subject_id = s.id \n" +
            "WHERE s.subject_manager_id=?1\n" +
            "ORDER BY ss.subject_id, ss.type_id, display_order", nativeQuery = true)
    List<SubjectSetting> findSubjectSettingByManager(int subjectManagerId);
    @Query(value="SELECT ss.* FROM subject_setting ss join subject s on ss.subject_id = s.id \n" +
            "WHERE s.subject_manager_id= :subjectManagerId and (:subjectId = -1 OR ss.subject_id= :subjectId ) and (:typeId = -1 OR ss.type_id= :typeId ) and (:status = -1 OR ss.status = :status) \n" +
            "ORDER BY subject_id, type_id, display_order", nativeQuery = true)
    List<SubjectSetting> filter(
            @Param("subjectManagerId") int subjectManagerId,
            @Param("subjectId") Integer subjectId,
            @Param("typeId") Integer typeId,
            @Param("status") Integer status);
}
