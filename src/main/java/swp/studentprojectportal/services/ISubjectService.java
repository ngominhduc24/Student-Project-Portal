package swp.studentprojectportal.services;

import swp.studentprojectportal.model.Subject;

import java.util.List;

public interface ISubjectService {

    List<Subject> getAllSubjects();

    Subject getSubjectById(Integer Id);

    Subject saveSubject(Subject subject);

    boolean updateSubject(int Id, String subjectName, String subjectCode, int subjectManagerId, boolean status);

    boolean updateSubjectStatus(int Id, boolean status);

    boolean addSubject(String subjectName, String subjectCode, int subjectManagerId, boolean status);

    boolean checkSubjectCodeExist(String subjectCode);

    boolean checkSubjectNameExist(String subjectName);
}
