package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.ClassAssignment;
@Repository
public interface IClassAssignmentRepository extends JpaRepository<ClassAssignment, Integer> {

}
