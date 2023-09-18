package swp.studentprojectportal.services.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.repository.ISubjectRepository;
import swp.studentprojectportal.repository.IUserRepository;
import swp.studentprojectportal.services.ISubjectService;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectSevice implements ISubjectService {
    @Autowired
    ISubjectRepository subjectRepository;

    @Autowired
    IUserRepository userRepository;
    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }
    @Override
    public Subject getSubjectById(Integer Id) {
        return subjectRepository.findById(Id).orElse(null);
    }
    @Override
    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }
    @Override
    public boolean updateSubject(int Id, String subjectName, String subjectCode, int subjectManagerId, boolean status){
        Optional<Subject> subject = subjectRepository.findById(Id);

        if(subject.isEmpty()) return false;

        Subject subjectData = subject.get();
        subjectData.setSubjectName(subjectName);
        subjectData.setSubjectCode(subjectCode);
        subjectData.setUser(userRepository.findById(subjectManagerId).get());
        subjectData.setStatus(status);
        subjectRepository.save(subjectData);

        return true;
    }

    @Override
    public boolean updateSubjectStatus(int Id, boolean status) {
        Optional<Subject> subject = subjectRepository.findById(Id);

        if(subject.isEmpty()) return false;

        subject.get().setStatus(status);
        subjectRepository.save(subject.get());
        return true;
    }
}
