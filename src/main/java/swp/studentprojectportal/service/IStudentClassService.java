package swp.studentprojectportal.service;

import swp.studentprojectportal.model.StudentClass;

import java.util.List;

public interface IStudentClassService {
    public List<StudentClass> findAllByProjectId(int projectId);
}
