package swp.studentprojectportal.service;

import org.springframework.data.domain.Page;
import swp.studentprojectportal.model.Assignment;

import java.util.List;

public interface IAssignmentService {
    List<Assignment> findAllAssignment(Integer pageno, Integer pagesize);

    Page<Assignment> filter(int subjectManagerId, String search, Integer pageNo, Integer pageSize,
                                String sortBy, Integer sortType, Integer subjectId, Integer status);
    Assignment getAssignmentById(int assignmentId);

    Assignment addAssignment(String title, String description, int subjectId, boolean status);

    List<Assignment> findAssignmentByManager(int subjectManagerId);

    Assignment saveAssignment(Assignment assignment);

    List<Assignment> getAssignmentBySubjectId(int subjectId);

    boolean updateAssignment(int id, String title, String description, int subject, boolean status);

    boolean checkExistedAssignment(String title, Integer subjectId, Integer id);
}
