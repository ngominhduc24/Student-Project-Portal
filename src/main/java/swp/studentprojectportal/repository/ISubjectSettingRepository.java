package swp.studentprojectportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Query(value="SELECT ss.id, ss.type_id, ss.setting_title, ss.setting_value, ss.status, ss.display_order, ss.create_by, ss.create_at, ss.update_by, ss.update_at, ss.subject_id " +
            "FROM subject_setting ss join subject s on ss.subject_id = s.id \n" +
            "WHERE s.subject_manager_id= :subjectManagerId " +
            "and (LOWER(ss.subject_id) LIKE LOWER(CONCAT('%', :search, '%')) \n" +
            "OR LOWER(ss.display_order) LIKE LOWER(CONCAT('%', :search, '%')) \n" +
            "OR LOWER(ss.setting_title) LIKE LOWER(CONCAT('%', :search, '%'))\n" +
            "OR LOWER(ss.setting_value) LIKE LOWER(CONCAT('%', :search, '%'))\n" +
            "OR LOWER(s.subject_code) LIKE LOWER(CONCAT('%', :search, '%'))) \n" +
            "and (:subjectId = -1 OR ss.subject_id= :subjectId ) " +
            "and (:typeId = -1 OR ss.type_id= :typeId ) and (:status = -1 OR ss.status = :status) \n"
            , nativeQuery = true)
    Page<SubjectSetting> filter(
            @Param("subjectManagerId") int subjectManagerId,
            @Param("search") String search,
            @Param("subjectId") Integer subjectId,
            @Param("typeId") Integer typeId,
            @Param("status") Integer status, Pageable pageable);

    @Query(value ="SELECT * FROM subject_setting\n"+
            "WHERE subject_id = :subjectId AND type_id = :typeId AND setting_title = :title"
            ,nativeQuery = true)
    SubjectSetting findSubjectSettingByIdAndTypeIdAndSettingTitle(
            @Param("subjectId") int subjectId,
            @Param("typeId") int typeId,
            @Param("title") String title);
}
