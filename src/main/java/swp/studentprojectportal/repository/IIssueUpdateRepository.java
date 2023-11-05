package swp.studentprojectportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.IssueSetting;
import swp.studentprojectportal.model.IssueUpdate;

import java.util.List;
import java.util.Optional;

@Repository
public interface IIssueUpdateRepository extends JpaRepository<IssueUpdate, Integer> {
    public List<IssueUpdate> findAllByIssue_Id(Integer issueId);

    @Query(value="SELECT * FROM issue_update\n" +
            "WHERE issue_id = :issueId AND title = :title",nativeQuery = true)
    Optional<IssueUpdate> findIssueUpdateByIssueAndTitle(@Param("issueId") int issueId, @Param("title") String title);

    @Query(value="SELECT * FROM issue_update\n" +
            "WHERE (issue_id = :issueId)\n" +
            "and (LOWER(title) LIKE LOWER(CONCAT('%', :search, '%')) \n" +
            "OR LOWER(description) LIKE LOWER(CONCAT('%', :search, '%'))) \n"
            , nativeQuery = true)
    Page<IssueUpdate> filterWork(@Param("issueId") Integer issueId,
                                               @Param("search") String search, Pageable pageable);
}
