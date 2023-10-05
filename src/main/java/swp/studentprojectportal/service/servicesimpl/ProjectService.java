package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Project;
import swp.studentprojectportal.repository.IProjectRepository;
import swp.studentprojectportal.service.IProjectService;

import java.util.List;

@Service
public class ProjectService implements IProjectService {
    @Autowired
    IProjectRepository projectRepository;

    @Override
    public List<Project> findAllByClassId(int classId) {
        return classId==0 ? projectRepository.findAll() : projectRepository.findAllByAclass_Id(classId);
    }

    @Override
    public Project findById(int projectId) {
        return projectRepository.findById(projectId).get();
    }

    @Override
    public List<Project> findAllByClassManagerId(int classManagerId) {
        return projectRepository.findAllByAclassUserId(classManagerId);
    }

    @Override
    public Project addNewProject(String title, String groupName, String description, int classId) {
        Project project = new

        return null;
    }


}
