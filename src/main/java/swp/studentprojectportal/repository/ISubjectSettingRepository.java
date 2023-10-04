package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.SubjectSetting;

import java.util.List;
@Repository
public interface ISubjectSettingRepository extends JpaRepository<SubjectSetting, Integer> {
    @Query(value="SELECT ss.* FROM subject_setting ss join subject s on ss.subject_id = s.id \n" +
            "WHERE s.subject_manager_id=?1\n" +
            "ORDER BY ss.subject_id, ss.type_id, display_order", nativeQuery = true)
    List<SubjectSetting> findSubjectSettingByManager(int subjectManagerId);
//    @Query(value="SELECT ss.* FROM subject_setting ss join subject s on ss.subject_id = s.id \n" +
//            "WHERE s.subject_manager_id= :subjectManagerId and (':subjectId' = 'ss.subject_id' ) and (':typeId' = 'ss.type_id' ) and (':status' = 'ss.status' ) \n" +
//            "ORDER BY subject_id, type_id, display_order", nativeQuery = true)
//    List<SubjectSetting> filterSubjectSettingBySubjectAndTypeAndStatus(@Param("subjectManagerId") int subjectManagerId,@Param("subjectId") String subjectId,@Param("typeId") String typeId,@Param("status") String status);

    List<SubjectSetting> findSubjectSettingBySubject(Subject subject);

    List<SubjectSetting> findSubjectSettingBySubjectAndTypeId(Subject subject, int typeId);

    List<SubjectSetting> findSubjectSettingBySubjectAndStatus(Subject subject, boolean status);

    List<SubjectSetting> findSubjectSettingBySubjectAndTypeIdAndStatus(Subject subject, int typeId, boolean status);

    @Query(value="SELECT ss.* FROM subject_setting ss join subject s on ss.subject_id = s.id \n" +
            "WHERE s.subject_manager_id= ?1 and ss.type_id = ?2 \n" +
            "ORDER BY subject_id, type_id, display_order", nativeQuery = true)
    List<SubjectSetting> findSubjectSettingByManagerAndTypeId(int subjectManagerId, int typeId);

    @Query(value="SELECT ss.* FROM subject_setting ss join subject s on ss.subject_id = s.id \n" +
            "WHERE s.subject_manager_id= ?1 and ss.status = ?2 \n" +
            "ORDER BY subject_id, type_id, display_order", nativeQuery = true)
    List<SubjectSetting> findSubjectSettingByManagerAndStatus(int subjectManagerId, int status);

    @Query(value="SELECT ss.* FROM subject_setting ss join subject s on ss.subject_id = s.id \n" +
            "WHERE s.subject_manager_id= ?1 and ss.type_id = ?2 and ss.status = ?3\n" +
            "ORDER BY subject_id, type_id, display_order", nativeQuery = true)
    List<SubjectSetting> findSubjectSettingByManagerAndTypeIdAndStatus(int subjectManagerId, int typeId, int status);




}
