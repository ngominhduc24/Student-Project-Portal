package swp.studentprojectportal.services;

import swp.studentprojectportal.model.Subject;

import java.util.List;

public interface ISubjectService {

    List<Subject> getAllSubjects();

    Subject getSubjectById(Integer Id);

    Subject saveSubject(Subject subject);

    Subject updateSubject(Integer Id, Subject subject);
}
