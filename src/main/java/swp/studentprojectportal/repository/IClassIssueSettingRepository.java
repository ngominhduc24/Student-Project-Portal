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

//    @Query(value = "SELECT cis.id, cis.class_id, cis.type, cis.status, cis.status_issue, cis.work_processes, cis.create_by, cis.create_at, cis.update_by, cis.update_at "+
//            "FROM class_issue_setting cis join class c on cis.class_id = c.id \n"+
//            "WHERE c.teacher_id= :teacherId "+
//            "and (LOWER(c.class_name) LIKE LOWER(CONCAT('%', :search, '%'))  " +
//            "and (LOWER(cis.type) LIKE LOWER(CONCAT('%', :search, '%'))  " +
//            "and (LOWER(cis.status_issue) LIKE LOWER(CONCAT('%', :search, '%'))  " +
//            "and (LOWER(cis.work_process) LIKE LOWER(CONCAT('%', :search, '%'))  " +
//            "and (:status = -1 OR cis.status = :status)",nativeQuery = true)
//    Page<ClassIssueSetting> filter(
//            @Param("subjectManagerId") int teacherId,
//            @Param("search") String search,
//            @Param("status") Integer status, Pageable pageable);
}
