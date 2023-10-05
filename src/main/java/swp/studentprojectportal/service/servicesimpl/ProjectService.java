package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Project;
import swp.studentprojectportal.repository.IClassRepository;
import swp.studentprojectportal.repository.IProjectRepository;
import swp.studentprojectportal.service.IProjectService;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService implements IProjectService {
    @Autowired
    IProjectRepository projectRepository;
    @Autowired
    IClassRepository classRepository;
    @Autowired
    UserService userService;

    @Override
    public List<Project> findAllByClassId(int classId) {
        return classId==0 ? projectRepository.findAll() : projectRepository.findAllByAclass_Id(classId);
    }

    @Override
    public Project findById(int projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        return project.isPresent() ? project.get() : null;
    }

    @Override
    public List<Project> findAllByClassManagerId(int classManagerId) {
        return projectRepository.findAllByAclassUserId(classManagerId);
    }

    @Override
    public Project addNewProject(String title, String groupName, String description, int classId) {
        Project project = new Project();

        project.setTitle(title);
        project.setGroupName(groupName);
        project.setAclass(classRepository.findClassById(classId));
        project.setDescription(description);
        project.setStatus(false);

        projectRepository.save(project);

        return project;
    }

    @Override
    public Project update(int projectID, String title, String groupName, String description, int mentorId, int teamLeaderId) {
        Project project = findById(projectID);

        project.setTitle(title);
        project.setGroupName(groupName);
        project.setDescription(description);
        project.setProjectMentor(userService.findUserById(mentorId).get());
        if(teamLeaderId>0) project.setTeamLeader(userService.findUserById(teamLeaderId).get());

        projectRepository.save(project);

        return project;
    }

    @Override
    public Project updateStatus(int projectId, boolean status) {
        Project project = projectRepository.findById(projectId).get();

        project.setStatus(status);

        projectRepository.save(project);

        return project;
    }

    @Override
    public boolean deleteById(int projectID) {
        try{
            projectRepository.deleteById(projectID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
