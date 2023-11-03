package swp.studentprojectportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.IssueSetting;

import java.util.List;
import java.util.Optional;

@Repository
public interface IIssueSettingRepository extends JpaRepository<IssueSetting, Integer> {
    List<IssueSetting> findAllByAclass_Id(Integer classId);
    List<IssueSetting> findAllByProjectId(Integer classId);

    @Query(value="SELECT ss.* FROM issue_setting ss join subject s on ss.subject_id = s.id \n" +
            "            WHERE s.subject_manager_id=?1", nativeQuery = true)
    List<IssueSetting> findSubjectSettingByManager(int subjectManagerId);
    @Query(value="SELECT * FROM issue_setting\n" +
            "WHERE subject_id = :subjectId\n" +
            "and (LOWER(subject_id) LIKE LOWER(CONCAT('%', :search, '%')) \n" +
            "OR LOWER(setting_group) LIKE LOWER(CONCAT('%', :search, '%')) \n" +
            "OR LOWER(setting_title) LIKE LOWER(CONCAT('%', :search, '%'))\n" +
            "OR LOWER(description) LIKE LOWER(CONCAT('%', :search, '%'))) \n" +
            "and (:settingGroup like \"\" OR LOWER(setting_group) LIKE LOWER(:settingGroup)) " +
            "and (:status = -1 OR status = :status)"
            , nativeQuery = true)
    Page<IssueSetting> filter(@Param("subjectId") Integer subjectId,
                              @Param("search") String search,
                              @Param("settingGroup") String settingGroup,
                              @Param("status") Integer status, Pageable pageable);
    @Query(value="SELECT distinct setting_group FROM issue_setting WHERE subject_id= ?1", nativeQuery = true)
    List<String> findAllDistinctSettingGroup(Integer subjectId);

    @Query(value="SELECT * FROM issue_setting\n" +
            "WHERE ((subject_id = :subjectId AND status=1 )OR class_id = :classId)\n" +
            "and (LOWER(subject_id) LIKE LOWER(CONCAT('%', :search, '%')) \n" +
            "OR LOWER(setting_group) LIKE LOWER(CONCAT('%', :search, '%')) \n" +
            "OR LOWER(setting_title) LIKE LOWER(CONCAT('%', :search, '%'))\n" +
            "OR LOWER(description) LIKE LOWER(CONCAT('%', :search, '%'))) \n" +
            "and (:settingGroup like \"\" OR LOWER(setting_group) LIKE LOWER(:settingGroup)) " +
            "and (:status = -1 OR status = :status)"
            , nativeQuery = true)
    Page<IssueSetting> filterClassIssueSetting(@Param("subjectId") Integer subjectId,
                                               @Param("classId") Integer classId,
                              @Param("search") String search,
                              @Param("settingGroup") String settingGroup,
                              @Param("status") Integer status, Pageable pageable);

    @Query(value="SELECT distinct setting_group FROM issue_setting WHERE ((subject_id = ?1 AND status=1 )OR class_id = ?2)", nativeQuery = true)
    List<String> findAllDistinctClassSettingGroup(Integer subjectId, Integer classId);

    @Query(value="SELECT distinct setting_group FROM issue_setting WHERE (subject_id = ?1 AND status=1 ) OR (class_id = ?2 AND status=1) OR project_id= ?3", nativeQuery = true)
    List<String> findAllDistinctProjectSettingGroup(Integer subjectId, Integer classId, Integer projectId);

    @Query(value="SELECT * FROM issue_setting\n" +
            "WHERE class_id = :classId AND setting_group = :group AND setting_title = :title",nativeQuery = true)
    Optional<IssueSetting> findIssueSettingByAclassAndSettingGroupAndSettingTitle(@Param("classId") int classId, @Param("group") String settingGroup, @Param("title") String settingTitle);

    @Query(value="SELECT * FROM issue_setting\n" +
            "WHERE ((subject_id = :subjectId AND status=1 )OR (class_id = :classId AND status=1) OR project_id = :projectId) \n" +
            "and (LOWER(subject_id) LIKE LOWER(CONCAT('%', :search, '%')) \n" +
            "OR LOWER(setting_group) LIKE LOWER(CONCAT('%', :search, '%')) \n" +
            "OR LOWER(setting_title) LIKE LOWER(CONCAT('%', :search, '%'))\n" +
            "OR LOWER(description) LIKE LOWER(CONCAT('%', :search, '%'))) \n" +
            "and (:settingGroup like \"\" OR LOWER(setting_group) LIKE LOWER(:settingGroup)) " +
            "and (:status = -1 OR status = :status)"
            , nativeQuery = true)
    Page<IssueSetting> filterProjectIssueSetting(@Param("subjectId") Integer subjectId,
                                               @Param("classId") Integer classId,
                                                 @Param("projectId") Integer projectId,
                                               @Param("search") String search,
                                               @Param("settingGroup") String settingGroup,
                                               @Param("status") Integer status, Pageable pageable);
}
