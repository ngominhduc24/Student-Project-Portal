package swp.studentprojectportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Assignment;
import swp.studentprojectportal.model.ClassIssueSetting;
import java.util.List;
@Repository
public interface IClassIssueSettingRepository extends JpaRepository<ClassIssueSetting, Integer> {

    @Query(value = "SELECT cis.id, cis.class_id, cis.type, cis.status, cis.status_issue, cis.work_process, cis.create_by, cis.create_at, cis.update_by, cis.update_at "+
            "FROM class_issue_setting cis join class c on cis.class_id = c.id \n"+
            "WHERE c.teacher_id= :teacherId ",nativeQuery = true)
    List<ClassIssueSetting> getAll(int teacherId);


    @Query(value = "SELECT cis.id, cis.class_id, cis.type, cis.status, cis.status_issue, cis.work_process, cis.description, cis.create_by, cis.create_at, cis.update_by, cis.update_at\n" +
            "FROM class_issue_setting cis \n" +
            "JOIN class c ON cis.class_id = c.id\n" +
            "WHERE c.teacher_id = :teacherId \n" +
            "AND (\n" +
            "    LOWER(c.class_name) LIKE LOWER(CONCAT('%', :search, '%'))\n" +
            "    OR LOWER(cis.type) LIKE LOWER(CONCAT('%', :search, '%'))\n" +
            "    OR LOWER(cis.status_issue) LIKE LOWER(CONCAT('%', :search, '%'))\n" +
            "    OR LOWER(cis.work_process) LIKE LOWER(CONCAT('%', :search, '%'))\n" +
            ")\n" +
            "AND (:status = -1 OR cis.status = :status) AND (:classId = -1 OR cis.class_id = :classId )",nativeQuery = true)
    Page<ClassIssueSetting> filterByClassManager(@Param("teacherId") Integer teacherId, @Param("search") String search,
                                                 @Param("classId") Integer classId, @Param("status") Integer status,
                                                 Pageable pageable);


}
