package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Project;
import swp.studentprojectportal.model.StudentClass;
import swp.studentprojectportal.repository.IClassRepository;
import swp.studentprojectportal.repository.IProjectRepository;
import swp.studentprojectportal.repository.IStudentClassRepository;
import swp.studentprojectportal.repository.IUserRepository;
import swp.studentprojectportal.service.IProjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService implements IProjectService {
    @Autowired
    IProjectRepository projectRepository;
    @Autowired
    IClassRepository classRepository;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    IStudentClassRepository studentClassRepository;

    @Override
    public List<Project> findAllByClassId(int classId) {
        return classId==0 ? projectRepository.findAll() : projectRepository.findAllByAclass_Id(classId);
    }

    @Override
    public int projectCount() {
        return projectRepository.findAll().size();
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
    public Project addNewProject(String title, String groupName, String description, int classId, int mentorId) {
        Project project = new Project();

        project.setTitle(title);
        project.setGroupName(groupName);
        project.setAclass(classRepository.findClassById(classId));
        project.setDescription(description);
        project.setProjectMentor(userRepository.findUserById(mentorId));
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
        project.setProjectMentor(userRepository.findUserById(mentorId));
        if(teamLeaderId>0) project.setTeamLeader(userRepository.findUserById(teamLeaderId));

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

    @Override
    public boolean setLeader(Integer studentId, Integer projectId) {
        try {
            Project p = projectRepository.findById(projectId).get();

            p.setTeamLeader(userRepository.findUserById(studentId));
            projectRepository.save(p);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateNote(Integer studentId, String note) {
        try {
            StudentClass studentClass = studentClassRepository.findById(studentId).get();

            studentClass.getStudent().setNote(note);

            userRepository.save(studentClass.getStudent());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Project checkDuplicateGroupNameInClass(int classId, String groupName) {
        return projectRepository.findByAclass_IdAndGroupName(classId, groupName);
    }

    @Override
    public List<Project> findAllByProjectMentorId(Integer projectMentorId) {
        return projectRepository.findAllByProjectMentorId(projectMentorId);
    }

    @Override
    public List<Project> findAllByStudentUserId(Integer studentId) {
        List<StudentClass> studentClassList = studentClassRepository.findAllByStudentId(studentId);
        List<Project> projectList = new ArrayList<>();

        for (StudentClass studentClass : studentClassList)
            projectList.add(studentClass.getProject());

        return projectList;
    }

}
