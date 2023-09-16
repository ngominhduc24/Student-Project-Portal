package swp.studentprojectportal.services.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.repository.ISubjectRepository;
import swp.studentprojectportal.services.ISubjectService;

import java.util.List;

@Service
public class SubjectSevice implements ISubjectService {
    @Autowired
    ISubjectRepository repository;
    @Override
    public List<Subject> getAllSubjects() {
        return repository.findAll();
    }

    @Override
    public Subject getSubjectById(Integer Id) {
        return repository.findById(Id).orElse(null);
    }
    @Override
    public Subject saveSubject(Subject subject) {
        return repository.save(subject);
    }
    @Override
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
