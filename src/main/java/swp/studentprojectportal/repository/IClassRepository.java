package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Class;

@Repository
public interface IClassRepository extends JpaRepository<Class, Integer> {
}
