package swp.studentprojectportal.service;

import swp.studentprojectportal.model.Assignment;

import java.util.List;

public interface IAssignmentService {
    List<Assignment> findAllAssignment(Integer pageno, Integer pagesize);

    List<Assignment> findAssignmentByManager(int subjectManagerId);

    Assignment findById(int id);

    Assignment saveAssignment(Assignment assignment);
}
