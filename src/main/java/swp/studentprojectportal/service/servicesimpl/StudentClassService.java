package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.StudentClass;
import swp.studentprojectportal.repository.IStudentClassRepository;
import swp.studentprojectportal.service.IStudentClassService;

import java.util.List;

@Service
public class StudentClassService implements IStudentClassService {
    @Autowired
    IStudentClassRepository studentClassRepository;

    @Override
    public List<StudentClass> findAllByProjectId(int projectId) {
        return studentClassRepository.findAllByProjectId(projectId);
    }
}
