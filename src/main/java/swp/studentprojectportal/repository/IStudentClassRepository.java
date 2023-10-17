package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.StudentClass;
import swp.studentprojectportal.model.User;

import java.util.List;

@Repository
public interface IStudentClassRepository extends JpaRepository<StudentClass, Integer> {
    List<StudentClass> findAllByAclass_Id(int classId);

    List<StudentClass> findAllByProjectId(int projectId);

    List<StudentClass> findAllByAclass_IdAndProjectId(int classId, Integer projectId);

    StudentClass findStudentClassByStudent_IdAndAclass_Id(int studentId, int classId);

    List<StudentClass> findAllByAclassUserId(int classManagerId);
}
