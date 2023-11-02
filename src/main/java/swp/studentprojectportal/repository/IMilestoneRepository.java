package swp.studentprojectportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.Milestone;

import java.util.List;


@Repository
public interface IMilestoneRepository extends JpaRepository<Milestone, Integer> {
    Milestone findMilestoneById(Integer id);
    List<Milestone> findMilestoneByTitle(String title);
    List<Milestone> findMilestoneByAclass_Id(Integer classId);
    @Query(value="SELECT * FROM  milestone\n" +
            "WHERE class_id= :classId " +
            "and (LOWER(title) LIKE LOWER(CONCAT('%', :search, '%'))  " +
            "OR LOWER(description) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(start_date) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(end_date) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "and (:status = -1 OR status = :status)", nativeQuery = true)
    Page<Milestone> filterClassBySubjectManager(@Param("classId") Integer classId, @Param("search") String search,
                                            @Param("status") Integer status, Pageable pageable);

    @Query(value="SELECT * FROM  milestone\n" +
            "WHERE (class_id= :classId AND status=1) OR project_id = :projectId " +
            "and (LOWER(title) LIKE LOWER(CONCAT('%', :search, '%'))  " +
            "OR LOWER(description) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(start_date) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(end_date) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "and (:status = -1 OR status = :status)", nativeQuery = true)
    Page<Milestone> filterByProject(@Param("classId") Integer classId, @Param("projectId") Integer projectId, @Param("search") String search,
                                                @Param("status") Integer status, Pageable pageable);
}
