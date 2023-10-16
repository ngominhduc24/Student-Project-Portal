package swp.studentprojectportal.service;

import org.springframework.data.domain.Page;
import swp.studentprojectportal.model.Milestone;
import swp.studentprojectportal.model.Class;


import java.time.LocalDateTime;
import java.util.List;

public interface IMilestoneService {

    Page<Milestone> filterMilestone(int classId, String search, Integer pageNo,
                                    Integer pageSize, String sortBy, Integer sortType, Integer status);

    Milestone findMilestoneById(Integer id);

    Milestone save(Milestone milestone);

    void addClassAssignment(Class classA);

    boolean addNewMilestone(Integer classId,String subjectCode, String title, String description, LocalDateTime startDate, LocalDateTime endDate, int status);

}
