package swp.studentprojectportal.services;

import swp.studentprojectportal.model.Subject;

import java.util.List;

public interface ISubjectService {

    List<Subject> getAllSubjects();

    Subject getSubjectByCode(String subjectCode);

    Subject saveSubject(Subject subject);

    Subject updateSubject(Integer Id, Subject subject);
}
