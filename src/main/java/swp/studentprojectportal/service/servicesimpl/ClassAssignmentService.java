package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.ClassAssignment;
import swp.studentprojectportal.repository.IClassAssignmentRepository;
import swp.studentprojectportal.service.IClassAssignmentService;

import java.util.List;

@Service
public class ClassAssignmentService implements IClassAssignmentService {
    @Autowired
    IClassAssignmentRepository classAssignmentRepository;

    @Override
    public List<ClassAssignment> getClassAssignment(Integer pageNo, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ClassAssignment> classAssignmentPage = classAssignmentRepository.findAll(pageable);
        List<ClassAssignment> classAssignment = classAssignmentPage.getContent();
        return classAssignment;
    }
}
