package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Issue;

import java.util.List;

@Repository
public interface IIssueRepository extends JpaRepository<Issue, Integer> {
    List<Issue> findAllByProjectId(Integer studentId);

    @Query(value = "SELECT i.id, i.title, i.project_id, i.milestone_id, i.assignee_id, i.type_id, i.status_id, i.process_id, i.create_by, i.create_at, i.update_by, i.update_at " +
            "FROM issue i " +
            "JOIN project p ON i.project_id = p.id " +
            "JOIN milestone m ON i.milestone_id = m.id " +
            "JOIN user u ON i.assignee_id = u.id " +
            "WHERE p.group_name = :groupName " +
            "AND (LOWER(i.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(m.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.display_name) LIKE LOWER(CONCAT('%', :search, '%')))" +
            "AND (:projectId = -1 OR i.project_id = :projectId) " +
            "AND (:milestoneId = -1 OR i.milestone_id = :milestoneId) " +
            "AND (:assigneeId = -1 OR i.assignee_id = :assigneeId)",
            nativeQuery = true)
    List<Issue> filterIssue(@Param("search") String search,
                            @Param("projectId") Integer projectId, @Param("milestoneId") Integer milestoneId,
                            @Param("assigneeId") Integer assigneeId);
}
