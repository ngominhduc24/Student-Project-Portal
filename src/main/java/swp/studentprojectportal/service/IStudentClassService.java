package swp.studentprojectportal.service;

import org.springframework.data.domain.Page;
import swp.studentprojectportal.model.StudentClass;
import swp.studentprojectportal.model.User;

import java.util.List;

public interface IStudentClassService {
    List<StudentClass> findAllByProjectId(int projectId);
    boolean addNewStudentToClass(int classId, int studentId);

    List<StudentClass> findAllByClassId(int classId);

    List<StudentClass> findAllNoGroupInClass(int classId);

    boolean updateProjectId(Integer studentId, Integer projectId);

    boolean checkStudentInClass(int classId, int studentId);

    public List<StudentClass> findAllByClassManager(int classManagerId);

    public boolean removeAllStudentFromClass(int classId);

    public StudentClass findByStudentIdAndAclassId(Integer userId, Integer classId);

    public List<StudentClass> findAllByStudentId(Integer studentId);
    public Page<User> filter(Integer projectId, String search, Integer pageNo, Integer pageSize,
                             String sortBy, Integer sortType, Integer status);

}
