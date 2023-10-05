package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.repository.IAssignmentRepository;
import swp.studentprojectportal.service.IAssignmentService;
@Service
public class AssignmentService implements IAssignmentService {
    @Autowired
    IAssignmentRepository assignmentRepository;


}
