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
    //List<Subject> findSubjectPaging(Pageable pageable);

    Page<Subject> findSubjectBySubjectCodeAndSubjectName(String subjectCode, String subjectName, Pageable pageable);
    @Query(value = "SELECT * FROM subject s" +
            "WHERE (LOWER(s.subject_name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            "OR LOWER(s.subject_code) LIKE LOWER(CONCAT('%', :searchTerm, '%')))"+
            "AND u.subject_manager_id = :subjectManagerId" +
            "And u.status = :status",
             nativeQuery = true)
    Page<Subject> searchSubjectAndFilterByManagerAndStatus(@Param("searchTerm") String searchTerm, @Param("subjectManagerId") Integer subjectManagerId, Pageable pageable);
}
