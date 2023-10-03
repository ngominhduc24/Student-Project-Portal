package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import swp.studentprojectportal.repository.IClassRepository;
import swp.studentprojectportal.service.IClassService;

public class ClassService implements IClassService {
    @Autowired
    IClassRepository classRepository;
}
