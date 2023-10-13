package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Assignment;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.ClassAssignment;
import swp.studentprojectportal.repository.IAssignmentRepository;
import swp.studentprojectportal.repository.IClassAssignmentRepository;
import swp.studentprojectportal.service.IClassAssignmentService;

import java.util.List;

@Service
public class ClassAssignmentService implements IClassAssignmentService {
    @Autowired
    IClassAssignmentRepository classAssignmentRepository;
    @Autowired
    IAssignmentRepository assignmentRepository;

    @Override
    public List<ClassAssignment> getClassAssignment(Integer pageNo, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ClassAssignment> classAssignmentPage = classAssignmentRepository.findAll(pageable);
        List<ClassAssignment> classAssignment = classAssignmentPage.getContent();
        return classAssignment;
    }
    @Override
    public ClassAssignment save(ClassAssignment classAssignment) {
        return classAssignmentRepository.save(classAssignment);
    }

    @Override
    public void addClassAssignment(Class classA) {
        List<Assignment> assignmentList = assignmentRepository.findAssignmentBySubjectId(classA.getSubject().getId());
        for (Assignment ass : assignmentList) {
            ClassAssignment assign = new ClassAssignment();
            assign.setAssignment(ass);
            assign.setAclass(classA);
            save(assign);
        }
    }

}
