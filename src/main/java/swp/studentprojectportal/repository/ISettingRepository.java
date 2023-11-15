package swp.studentprojectportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Setting;

import java.util.List;

@Repository
public interface ISettingRepository extends JpaRepository<Setting, Integer> {
    Setting findSettingByTypeIdAndSettingTitleAndStatus(Integer typeId, String settingTitle, boolean status);
    List<Setting> findSettingByTypeIdOrderByDisplayOrder(Integer typeId);
    Setting findBySettingTitle(String settingTitle);
    Setting findByTypeIdAndDisplayOrder(int typeId, int displayOrder);
    Setting findTop1SettingByTypeIdOrderByDisplayOrderDesc(int typeId);
    @Query(value="SELECT distinct setting.* FROM class c join subject s on c.subject_id = s.id join setting on c.semester_id = setting.id\n" +
            "WHERE s.subject_manager_id = ?1", nativeQuery = true)
    List<Setting> findSemesterBySubjectManagerId(int subjectManagerId);
    List<Setting> findSemesterByTypeIdAndStatus(Integer typeId, Boolean status);
    @Query(value="SELECT distinct setting.* FROM class c join setting on c.semester_id = setting.id\n" +
            "WHERE c.teacher_id = ?1", nativeQuery = true)
    List<Setting> findSemesterByClassManagerId(int classManagerId);
    @Query(value="SELECT * FROM setting \n" +
            "WHERE (LOWER(setting_title) LIKE LOWER(CONCAT('%', :search, '%'))  \n" +
            "OR LOWER(display_order) LIKE LOWER(CONCAT('%', :search, '%'))) \n" +
            "and (:typeId = -1 OR type_id = :typeId) and (:status = -1 OR status = :status)", nativeQuery = true)
    Page<Setting> filter(String search, Integer typeId, Integer status, Pageable pageable);
}
