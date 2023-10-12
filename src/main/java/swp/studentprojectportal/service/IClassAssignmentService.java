package swp.studentprojectportal.service;

import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.ClassAssignment;
import swp.studentprojectportal.model.Class;


import java.util.List;

public interface IClassAssignmentService {

    List<ClassAssignment> getClassAssignment(Integer pageNo, Integer pageSize);
    ClassAssignment save(ClassAssignment classAssignment);
    void addClassAssignment(Class classA);
}
