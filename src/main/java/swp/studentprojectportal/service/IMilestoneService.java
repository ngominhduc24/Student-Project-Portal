package swp.studentprojectportal.service;

import org.springframework.data.domain.Page;
import swp.studentprojectportal.model.Milestone;
import swp.studentprojectportal.model.Class;


import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public interface IMilestoneService {

    Page<Milestone> filterMilestone(int classId, String search, Integer pageNo,
                                    Integer pageSize, String sortBy, Integer sortType, Integer status);

    Milestone findMilestoneById(Integer id);

    List<Milestone> findMilestoneByClassId(Integer classid);

    Milestone save(Milestone milestone);

    void addClassAssignment(Class classA);

    boolean addNewMilestone(Integer classId, String title, String description, Date startDate, Date endDate, int status);

    Milestone findMilestoneByTitle(String milestoneTitle);

    Page<Milestone> filterMilestoneByProject(int classId, int projectId, String search, Integer pageNo,
                                    Integer pageSize, String sortBy, Integer sortType, Integer status);

    List<Milestone> findAllByProjectMentor(Integer projectMentorId);

    List<Milestone> findAllByStudentId(Integer sutdentId);

    List<Milestone> findAllByProjectId(Integer projectId);
}
