package swp.studentprojectportal.service;

import org.springframework.data.domain.Page;
import swp.studentprojectportal.model.Assignment;
import swp.studentprojectportal.model.SubjectSetting;

import java.util.List;

public interface IAssignmentService {
    List<Assignment> findAllAssignment(Integer pageno, Integer pagesize);

    Page<Assignment> filter(int subjectManagerId, String search, Integer pageNo, Integer pageSize,
                                String sortBy, Integer sortType, Integer subjectId, Integer status);
    List<Assignment> findAssignmentByManager(int subjectManagerId);

    Assignment findById(int id);

    Assignment saveAssignment(Assignment assignment);
}
