package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.repository.IStudentClassRepository;
import swp.studentprojectportal.service.IStudentClassService;
@Service
public class StudentClassService implements IStudentClassService {
    @Autowired
    IStudentClassRepository studentClassRepository;
}
