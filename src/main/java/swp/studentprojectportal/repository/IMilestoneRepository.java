package swp.studentprojectportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.Milestone;



@Repository
public interface IMilestoneRepository extends JpaRepository<Milestone, Integer> {
    @Query(value="SELECT * FROM  milestone\n" +
            "WHERE class_id= :classId " +
            "and (LOWER(title) LIKE LOWER(CONCAT('%', :search, '%'))  " +
            "OR LOWER(description) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(start_date) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(end_date) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "and (:status = -1 OR status = :status)", nativeQuery = true)
    Page<Milestone> filterClassBySubjectManager(@Param("classId") Integer classId, @Param("search") String search,
                                            @Param("status") Integer status, Pageable pageable);
}
