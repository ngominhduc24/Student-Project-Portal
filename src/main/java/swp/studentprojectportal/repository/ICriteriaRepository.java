package swp.studentprojectportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Criteria;
import swp.studentprojectportal.model.Issue;
import swp.studentprojectportal.model.Setting;
@Repository
public interface ICriteriaRepository extends JpaRepository<Criteria, Integer> {
    @Query(value="SELECT * FROM criteria \n" +
            "WHERE assignment_id = :assignmentId " +
            "AND (LOWER(name) LIKE LOWER(CONCAT('%', :search, '%'))  \n" +
            "OR LOWER(weight) LIKE LOWER(CONCAT('%', :search, '%'))) \n" +
            "and (:status = -1 OR status = :status)", nativeQuery = true)
    Page<Criteria> filter(@Param("assignmentId") Integer assignmentId, String search, Integer status, Pageable pageable);
}
