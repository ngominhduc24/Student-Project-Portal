package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Assignment;
import swp.studentprojectportal.repository.IAssignmentRepository;
import swp.studentprojectportal.service.IAssignmentService;

import java.util.List;

@Service
public class AssignmentService implements IAssignmentService {
    @Autowired
    IAssignmentRepository assignmentRepository;

    @Override
    public List<Assignment> findAllAssignment(Integer pageno, Integer pagesize){
        Pageable pageable = PageRequest.of(pageno,pagesize);
        Page<Assignment> assignmentPage = assignmentRepository.findAll(pageable);
        List<Assignment> assignment = assignmentPage.getContent();
        return assignment;
    }

    @Override
    public List<Assignment> findAssignmentByManager(int subjectManagerId){
        return assignmentRepository.findAssignmentByManager(subjectManagerId);
    }

    @Override
    public Assignment findById(int id){
        return assignmentRepository.findById(id).get();
    }

    @Override
    public Assignment saveAssignment(Assignment assignment){
        return  assignmentRepository.save(assignment);
    }

}
