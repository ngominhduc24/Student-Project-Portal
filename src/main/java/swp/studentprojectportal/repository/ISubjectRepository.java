package swp.studentprojectportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISubjectRepository extends JpaRepository<Subject, Integer> {
    Subject findSubjectBySubjectName(String subjectName);
    Subject findSubjectBySubjectCode(String subjectCode);
    List<Subject> findAllSubjectByUser(User user);
    List<Subject> findAllSubjectByUserAndStatus(User user, Boolean status);
    @Query(value = "SELECT distinct s.* FROM class c join subject s on c.subject_id = s.id\n" +
            "WHERE c.teacher_id = ?1"
            ,nativeQuery = true)
    List<Subject> findAllSubjectByClassManagerId(Integer classManagerId);
    //List<Subject> findSubjectPaging(Pageable pageable);

    Page<Subject> findSubjectBySubjectCodeContainsIgnoreCaseOrSubjectNameContainsIgnoreCase(String subjectCode, String subjectName, Pageable pageable);
    @Query(value = "SELECT * FROM subject s " +
            "WHERE (LOWER(s.subject_name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            "OR LOWER(s.subject_code) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
            "AND s.subject_manager_id = :subjectManagerId"
            ,nativeQuery = true)
    Page<Subject> searchSubjectAndFilterByManager(@Param("searchTerm") String searchTerm, @Param("subjectManagerId") Integer subjectManagerId, Pageable pageable);

    @Query(value = "SELECT * FROM subject s " +
            "WHERE (LOWER(s.subject_name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            "OR LOWER(s.subject_code) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
            "AND s.status = :status"
            ,nativeQuery = true)
    Page<Subject> searchSubjectAndFilterByStatus(@Param("searchTerm") String searchTerm, @Param("status") Integer status, Pageable pageable);

    @Query(value = "SELECT * FROM subject s " +
            "WHERE (LOWER(s.subject_name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            "OR LOWER(s.subject_code) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
            "AND s.status = :status " +
            "AND s.subject_manager_id = :subjectManagerId"
            ,nativeQuery = true)
    Page<Subject> searchSubjectAndFilterByManagerAndStatus(@Param("searchTerm") String searchTerm, @Param("subjectManagerId") Integer subjectManagerId, @Param("status") Integer status, Pageable pageable);
}
