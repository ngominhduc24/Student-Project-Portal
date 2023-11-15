package swp.studentprojectportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Assignment;

import java.util.List;

@Repository
public interface IAssignmentRepository extends JpaRepository<Assignment, Integer> {
    Assignment findById(int id);
    List<Assignment> findAssignmentBySubjectId(int subjectId);
    @Query(value = "SELECT ass.* FROM assignment ass join subject s on ass.subject_id = s.id \n"+
            "WHERE s.subject_manager_id=?1\n"+
            "ORDER BY ass.subject_id,ass.title",nativeQuery = true)
    List<Assignment> findAssignmentByManager(int subjectManagerID);

    @Query(value = "SELECT ass.id, ass.subject_id, ass.title, ass.status, ass.description, ass.is_subject_assignment, ass.create_by, ass.create_at, ass.update_by, ass.update_at "+
                    "FROM assignment ass join subject s on ass.subject_id = s.id \n"+
                    "WHERE s.subject_manager_id= :subjectManagerId "+
            "and (LOWER(ass.subject_id) LIKE LOWER(CONCAT('%', :search, '%')) \n" +
            "OR LOWER(ass.title) LIKE LOWER(CONCAT('%', :search, '%'))\n" +
            "OR LOWER(ass.description) LIKE LOWER(CONCAT('%', :search, '%'))) \n" +
            "and (:subjectId = -1 OR ass.subject_id= :subjectId ) " +
            "and (:status = -1 OR ass.status = :status) \n",nativeQuery = true)
    Page<Assignment> filter(
            @Param("subjectManagerId") int subjectManagerId,
            @Param("search") String search,
            @Param("subjectId") Integer subjectId,
            @Param("status") Integer status, Pageable pageable);

    Assignment findByTitleAndSubjectId(String title, Integer subjectId);
}
