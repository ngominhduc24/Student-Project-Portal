package swp.studentprojectportal.service;

import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.User;

import java.util.List;

public interface ISubjectService {

    List<Subject> getAllSubjects();

    int subjectCount();

    List<Subject> getSubject(Integer pageNo, Integer pageSize, String search, Integer subjectManagerId, Integer status);

    Subject getSubjectById(Integer Id);

    Subject saveSubject(Subject subject);

    boolean updateSubject(int Id, String subjectName, String subjectCode, int subjectManagerId, boolean status);

    boolean updateSubjectStatus(int Id, boolean status);

    Subject addSubject(String subjectName, String subjectCode, int subjectManagerId, boolean status);

    boolean checkSubjectCodeExist(String subjectCode);

    boolean checkSubjectNameExist(String subjectName);

    List<Subject> findAllSubjectByUser(User user);
    List<Subject> findAllSubjectByClassManagerId(Integer classManagerId);
    List<Subject> findAllSubjectByUserAndStatus(User user, Boolean status);
    List<Subject> findSubjectForProject(Integer projectMentorId);
}
