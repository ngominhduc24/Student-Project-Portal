package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Assignment;

import java.util.List;

@Repository
public interface IAssignmentRepository extends JpaRepository<Assignment, Integer> {
    Assignment findAssignmentByTitle(String title);
    Assignment findById(int id);
    Assignment findAssignmentByDescription(String Description);
}
