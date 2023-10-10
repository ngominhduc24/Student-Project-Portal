package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Assignment;

import java.util.List;

@Repository
public interface IAssignmentRepository extends JpaRepository<Assignment, Integer> {
    @Query(value = "SELECT ass.* FROM assignment ass join subject s on ass.subject_id = s.id \n"+
            "WHERE s.subject_manager_id=?1\n"+
            "ORDER BY ass.subject_id,ass.title",nativeQuery = true)
    List<Assignment> findAssignmentByManager(int subjectManagerID);
}
