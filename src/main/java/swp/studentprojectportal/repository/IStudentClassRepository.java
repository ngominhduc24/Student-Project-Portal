package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.StudentClass;
@Repository
public interface IStudentClassRepository extends JpaRepository<StudentClass, Integer> {
}
