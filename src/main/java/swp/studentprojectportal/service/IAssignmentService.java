package swp.studentprojectportal.service;

import swp.studentprojectportal.model.Assignment;

import java.util.List;

public interface IAssignmentService {
    List<Assignment> findAllAssignment(Integer pageno, Integer pagesize);

    Assignment getAssignmentById(int assignmentId);

    Assignment addAssignment(String title, String description, int subjectId, boolean status);

    List<Assignment> findAssignmentByManager(int subjectManagerId);

    Assignment saveAssignment(Assignment assignment);
}
