package swp.studentprojectportal.service;

import swp.studentprojectportal.model.StudentClass;

import java.util.List;

public interface IStudentClassService {
    List<StudentClass> findAllByProjectId(int projectId);
    boolean addNewStudentToClass(int classId, int studentId);
}
