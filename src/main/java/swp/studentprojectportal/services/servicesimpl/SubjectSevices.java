package swp.studentprojectportal.services.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.repository.ISubjectRepository;

import java.util.List;

@Service
public class SubjectSevices {
    @Autowired
    ISubjectRepository repository;

    public List<Subject> getAllSubjects() {
        return repository.findAll();
    }

    public Subject getSubjectByCode(String subjectCode){
        return repository.findBySubjectCode(subjectCode);
    }


    public Subject saveSubject(Subject subject) {
        return repository.save(subject);
    }

    public Subject updateSubject(Integer Id, Subject subject) {
        Subject existingSubject = repository.findById(Id).orElse(null);
        assert existingSubject != null;
        existingSubject.setSubjectName(subject.getSubjectName());
        existingSubject.setSubjectCode(subject.getSubjectCode());
        existingSubject.setDescription(subject.getDescription());
        existingSubject.setUser(subject.getUser());
        return repository.save(existingSubject);
    }
}
