package swp.studentprojectportal.service;

import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Project;

import java.util.List;

public interface IProjectService {
    List<Project> findAllByClassId (int classId);

    Project findById(int projectId);

    List<Project> findAllByClassManagerId (int classManagerId);

    Project addNewProject(String title, String groupName, String description, int classId, int mentorId);

    Project update(int projectID, String title, String groupName, String description, int teamLeaderId, int mentorId);

    Project updateStatus(int projectID, boolean status);

    boolean deleteById(int projectID);
}
