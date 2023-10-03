package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.repository.IClassAssignmentRepository;
import swp.studentprojectportal.service.IClassAssignmentService;
@Service
public class ClassAssignmentService implements IClassAssignmentService {
    @Autowired
    IClassAssignmentRepository classAssignmentRepository;
}
