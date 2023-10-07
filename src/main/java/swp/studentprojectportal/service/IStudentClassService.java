package swp.studentprojectportal.service;

import swp.studentprojectportal.model.StudentClass;

import java.util.List;

public interface IStudentClassService {
    public List<StudentClass> findAllByProjectId(int projectId);

    public List<StudentClass> findAllByClassId(int classId);

    public List<StudentClass> findAllNoGroupInClass(int classId);

    public boolean updateProjectId(Integer studentId, Integer projectId);

}
