package swp.studentprojectportal.service;

import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.ClassAssignment;

import java.util.List;

public interface IClassAssignmentService {

    List<ClassAssignment> getClassAssignment(Integer pageNo, Integer pageSize);
}
