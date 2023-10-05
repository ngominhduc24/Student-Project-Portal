package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Class;

import java.util.List;

@Repository
public interface IClassRepository extends JpaRepository<Class, Integer> {
    Class findClassById(Integer className);

    List<Class> findAllByUserId(Integer classManagerId);
}
