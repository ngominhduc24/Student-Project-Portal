package swp.studentprojectportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.IssueSetting;

import java.util.List;
@Repository
public interface IIssueSettingRepository extends JpaRepository<IssueSetting, Integer> {
    @Query(value="SELECT ss.* FROM issue_setting ss join subject s on ss.subject_id = s.id \n" +
            "            WHERE s.subject_manager_id=?1", nativeQuery = true)
    List<IssueSetting> findSubjectSettingByManager(int subjectManagerId);
    @Query(value="SELECT ss.id, ss.setting_group, ss.setting_title, ss.description, ss.status, ss.create_by, ss.create_at, ss.update_by, ss.update_at, ss.subject_id , ss.class_id, ss.project_id\n" +
            "FROM issue_setting ss join subject s on ss.subject_id = s.id \n" +
            "WHERE subject_id = :subjectId\n" +
            "and (LOWER(ss.subject_id) LIKE LOWER(CONCAT('%', :search, '%')) \n" +
            "OR LOWER(ss.setting_group) LIKE LOWER(CONCAT('%', :search, '%')) \n" +
            "OR LOWER(ss.setting_title) LIKE LOWER(CONCAT('%', :search, '%'))\n" +
            "OR LOWER(ss.description) LIKE LOWER(CONCAT('%', :search, '%'))) \n" +
            "and (:settingGroup like \"\" OR LOWER(ss.setting_group) LIKE LOWER(:settingGroup)) " +
            "and (:status = -1 OR ss.status = :status)"
            , nativeQuery = true)
    Page<IssueSetting> filter(@Param("subjectId") Integer subjectId,
                              @Param("search") String search,
                              @Param("settingGroup") String settingGroup,
                              @Param("status") Integer status, Pageable pageable);
    @Query(value="SELECT distinct setting_group FROM issue_setting WHERE subject_id= ?1", nativeQuery = true)
    List<String> findAllDistinctSettingGroup(Integer subjectId);
}
