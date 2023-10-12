package swp.studentprojectportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.User;

import java.util.List;

@Repository
public interface IClassRepository extends JpaRepository<Class, Integer> {
    Class findClassById(Integer className);
    Class findClassByClassNameAndSubjectId(String className, Integer subjectId);


    Class findClassByClassName(String className);

    List<Class> findAllByUserId(Integer classManagerId);

    @Query(value = "SELECT DISTINCT c.id, c.class_name, c.description, c.subject_id, c.semester_id, c.teacher_id, c.status, c.create_by, c.create_at, c.update_by, c.update_at\n" +
            "FROM class c JOIN class_issue_setting cis ON c.id = cis.class_id WHERE c.teacher_id = :teacherId", nativeQuery = true)
    List<Class> findClassForIssue(@Param("teacherId") Integer teacherId);
    @Query(value="SELECT c.id, c.class_name, c.description, c.subject_id, c.semester_id, c.teacher_id, c.status, c.create_by, c.create_at, c.update_by, c.update_at " +
            "FROM class c join subject s on c.subject_id = s.id join setting st on c.semester_id = st.id join user u on c.teacher_id = u.id\n" +
            "WHERE s.subject_manager_id = :subjectManagerId " +
            "and (LOWER(c.class_name) LIKE LOWER(CONCAT('%', :search, '%'))  " +
            "OR LOWER(s.subject_code) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(st.setting_title) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.full_name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "and (:subjectId = -1 OR c.subject_id = :subjectId) and (:semesterId = -1 OR c.semester_id = :semesterId) " +
            "and (:teacherId = -1 OR c.teacher_id=:teacherId) and (:status = -1 OR c.status = :status)", nativeQuery = true)
    Page<Class> filterClassBySubjectManager(@Param("subjectManagerId") Integer subjectManagerId, @Param("search") String search,
                                          @Param("subjectId") Integer subjectId, @Param("semesterId") Integer semesterId,
                                          @Param("teacherId") Integer teacherId, @Param("status") Integer status,
                                          Pageable pageable);

    @Query(value="SELECT c.id, c.class_name, c.description, c.subject_id, c.semester_id, c.teacher_id, c.status, c.create_by, c.create_at, c.update_by, c.update_at " +
            "FROM class c join subject s on c.subject_id = s.id join setting st on c.semester_id = st.id join user u on c.teacher_id = u.id\n" +
            "WHERE c.teacher_id = :teacherId " +
            "and (LOWER(c.class_name) LIKE LOWER(CONCAT('%', :search, '%'))  " +
            "OR LOWER(s.subject_code) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(st.setting_title) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "and (:subjectId = -1 OR c.subject_id = :subjectId) and (:semesterId = -1 OR c.semester_id = :semesterId) " +
            "and (:status = -1 OR c.status = :status)", nativeQuery = true)
    Page<Class> filterClassByClassManager(@Param("teacherId") Integer teacherId, @Param("search") String search,
                                            @Param("subjectId") Integer subjectId, @Param("semesterId") Integer semesterId,
                                            @Param("status") Integer status,
                                            Pageable pageable);


}
