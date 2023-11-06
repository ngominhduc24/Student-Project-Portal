package swp.studentprojectportal.service;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.Project;
import swp.studentprojectportal.model.Setting;
import swp.studentprojectportal.model.StudentClass;

import java.util.List;

public interface IProjectService {
    List<Project> findAllByClassId (int classId);

    Project findById(int projectId);

    List<Project> findAllByClassManagerId (int classManagerId);

    Project addNewProject(String title, String groupName, String description, int classId, int mentorId);

    Project update(int projectID, String title, String groupName, String description, int teamLeaderId, int mentorId);

    Project updateStatus(int projectID, boolean status);

    boolean deleteById(int projectID);

    public boolean setLeader(Integer studentId, Integer projectId);

    public boolean updateNote(Integer studentId, String note);

    public Project checkDuplicateGroupNameInClass(int classId, String groupName);

    public List<Project> findAllByProjectMentorId(Integer projectMentorId);

    public List<Project> findAllByStudentUserId(Integer studentId);
    public List<StudentClass> findAllByProjectId(int classId);
    public Page<Project> filterByProjectMentor(Integer projectMentorId, String search, Integer pageNo, Integer pageSize,
                         String sortBy, Integer sortType, Integer classId, Integer subjectId, Integer status);
    public Page<Project> filterByStudent(Integer studentId, String search, Integer pageNo, Integer pageSize,
                                               String sortBy, Integer sortType, Integer status);
}
