package swp.studentprojectportal.service;

import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Project;

import java.util.List;

public interface IProjectService {
    List<Project> findAllByClassId (int classId);

    int projectCount();

    Project findById(int projectId);

    List<Project> findAllByClassManagerId (int classManagerId);

    Project addNewProject(String title, String groupName, String description, int classId, int mentorId);

    Project update(int projectID, String title, String groupName, String description, int teamLeaderId, int mentorId);

    Project updateStatus(int projectID, boolean status);

    boolean deleteById(int projectID);

    public boolean setLeader(Integer studentId, Integer projectId);

    public boolean updateNote(Integer studentId, String note);

    public Project checkDuplicateGroupNameInClass(int classId, String groupName);
}
