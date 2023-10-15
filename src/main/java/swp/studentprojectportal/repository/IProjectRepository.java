package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Project;

import java.util.List;

@Repository
public interface IProjectRepository extends JpaRepository<Project, Integer> {
    public List<Project> findAllByAclass_Id(int classId);

    public List<Project> findAllByAclassUserId(int teacherId);

    public Project findByAclass_IdAndGroupName(int classId, String groupName);
}
