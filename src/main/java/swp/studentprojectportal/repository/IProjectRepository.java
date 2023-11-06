package swp.studentprojectportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Project;

import java.util.List;

@Repository
public interface IProjectRepository extends JpaRepository<Project, Integer> {
    public List<Project> findAllByAclass_Id(int classId);

    public List<Project> findAllByAclassUserId(int teacherId);

    public Project findByAclass_IdAndGroupName(int classId, String groupName);

    public List<Project> findAllByProjectMentorId(Integer projectMentorId);

    @Query(value="SELECT p.id, p.class_id, p.project_mentor_id, p.team_leader_id, p.title, p.status, p.group_name, p.description, p.create_by, p.create_at, p.update_by, p.update_at FROM project p join class c on p.class_id = c.id join subject s on s.id = c.subject_id \n" +
            "WHERE p.project_mentor_id = :projectMentorId AND " +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%'))  \n" +
            "OR LOWER(p.group_name) LIKE LOWER(CONCAT('%', :search, '%'))" +
            "OR LOWER(c.class_name) LIKE LOWER(CONCAT('%', :search, '%'))" +
            "OR LOWER(s.subject_code) LIKE LOWER(CONCAT('%', :search, '%'))) \n" +
            "and (:classId = -1 OR p.class_id = :classId) and (:subjectId = -1 OR c.subject_id = :subjectId) and (:status = -1 OR p.status = :status)", nativeQuery = true)
    Page<Project> filterByProjectMentor(Integer projectMentorId, String search, Integer classId, Integer subjectId, Integer status, Pageable pageable);

    @Query(value="SELECT p.id, p.class_id, p.project_mentor_id, p.team_leader_id, p.title, p.status, p.group_name, p.description, p.create_by, p.create_at, p.update_by, p.update_at FROM project p join class c on p.class_id = c.id join subject s on s.id = c.subject_id join student_class sc on p.id = sc.project_id \n" +
            "WHERE sc.student_id = :studentId AND" +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%'))  \n" +
            "OR LOWER(p.group_name) LIKE LOWER(CONCAT('%', :search, '%')) \n" +
            "OR LOWER(c.class_name) LIKE LOWER(CONCAT('%', :search, '%'))" +
            "OR LOWER(s.subject_code) LIKE LOWER(CONCAT('%', :search, '%'))) \n" +
            "and (:status = -1 OR p.status = :status)", nativeQuery = true)
    Page<Project> filterByStudent(Integer studentId, String search, Integer status, Pageable pageable);
}
