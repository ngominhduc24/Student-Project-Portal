package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.ISettingRepository;
import swp.studentprojectportal.repository.ISubjectRepository;
import swp.studentprojectportal.repository.IUserRepository;
import swp.studentprojectportal.service.ISubjectService;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService implements ISubjectService {
    @Autowired
    ISubjectRepository subjectRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ISettingRepository settingRepository;

    @Override
    public Subject findSubjectById(int id){
        return subjectRepository.findById(id).get();
    }
    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public List<Subject> getSubject(Integer pageNo, Integer pageSize, String search, Integer subjectManagerId, Integer status) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        if(status != -1 && subjectManagerId != -1) {
            return subjectRepository.searchSubjectAndFilterByManagerAndStatus(search,subjectManagerId,status,pageable).getContent();
        } else if(status != -1) {
            return subjectRepository.searchSubjectAndFilterByStatus(search,status,pageable).getContent();
        } else if(subjectManagerId != -1) {
            return subjectRepository.searchSubjectAndFilterByManager(search,subjectManagerId,pageable).getContent();
        } else {
            return subjectRepository.findSubjectBySubjectCodeContainsIgnoreCaseOrSubjectNameContainsIgnoreCase(search, search, pageable).getContent();
        }
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

    @Override
    public Subject addSubject(String subjectName, String subjectCode, int subjectManagerId, boolean status) {
        Subject subject = new Subject();

        subject.setSubjectName(subjectName);
        subject.setSubjectCode(subjectCode);
        subject.setUser(userRepository.findById(subjectManagerId).get());
        subject.setStatus(true);

        subjectRepository.save(subject);
        return subject;
    }
    @Override
    public boolean checkSubjectCodeExist(String subjectCode) {
        if (subjectRepository.findSubjectBySubjectCode(subjectCode) != null) {
            return true;
        }
        return false;
    }
    @Override
    public boolean checkSubjectNameExist(String subjectName) {
        if (subjectRepository.findSubjectBySubjectName(subjectName) != null) {
            return true;
        }
        return false;
    }
    @Override
    public List<Subject> findAllSubjectByUser(User user) {
        return subjectRepository.findAllSubjectByUser(user);
    }

    @Override
    public List<Subject> findAllSubjectByClassManagerId(Integer classManagerId) {
        return subjectRepository.findAllSubjectByClassManagerId(classManagerId);
    }

    @Override
    public List<Subject> findAllSubjectByUserAndStatus(User user, Boolean status) {
        return subjectRepository.findAllSubjectByUserAndStatus(user, status);
    }

    @Override
    public List<Subject> findSubjectForProject(Integer projectMentorId) {
        return subjectRepository.findSubjectByClassesProjectsProjectMentorId(projectMentorId);
    }

    public Object getTotalPage(int pageSize) {
        long count = subjectRepository.count();
        int totalPage = count % pageSize == 0 ? (int) (count/pageSize) : (int) (count/pageSize) +1;
        return  totalPage;
    }
}
