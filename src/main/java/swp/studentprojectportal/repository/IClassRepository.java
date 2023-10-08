package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Class;

import java.util.List;

@Repository
public interface IClassRepository extends JpaRepository<Class, Integer> {
    Class findClassById(Integer className);

    List<Class> findAllByUserId(Integer classManagerId);
    @Query(value="SELECT c.* FROM class c join subject s on c.subject_id = s.id\n" +
            "WHERE s.subject_manager_id = ?1", nativeQuery = true)
    List<Class> findAllBySubjectManagerId(Integer subjectManagerId);
    List<Class> findAllBySubjectId(Integer subjectId);
}
