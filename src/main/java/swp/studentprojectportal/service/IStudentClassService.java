package swp.studentprojectportal.service;

import swp.studentprojectportal.model.StudentClass;

import java.util.List;

public interface IStudentClassService {
    List<StudentClass> findAllByProjectId(int projectId);
    boolean addNewStudentToClass(int classId, int studentId);

    List<StudentClass> findAllByClassId(int classId);

    List<StudentClass> findAllNoGroupInClass(int classId);

    boolean updateProjectId(Integer studentId, Integer projectId);

    boolean checkStudentInClass(int classId, int studentId);

    public List<StudentClass> findAllByClassManager(int classManagerId);

}
