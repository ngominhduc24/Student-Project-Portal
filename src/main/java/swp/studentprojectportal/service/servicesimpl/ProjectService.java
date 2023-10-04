package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.repository.IProjectRepository;
import swp.studentprojectportal.service.IProjectService;
@Service
public class ProjectService implements IProjectService {
    @Autowired
    IProjectRepository projectRepository;
}
