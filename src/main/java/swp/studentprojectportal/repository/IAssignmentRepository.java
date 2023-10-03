package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Assignment;

@Repository
public interface IAssignmentRepository extends JpaRepository<Assignment, Integer> {

}
