package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Project;
@Repository
public interface IProjectRepository extends JpaRepository<Project, Integer> {
}
