package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Assignment;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.repository.IAssignmentRepository;
import swp.studentprojectportal.repository.ISubjectRepository;
import swp.studentprojectportal.service.IAssignmentService;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService implements IAssignmentService {
    @Autowired
    IAssignmentRepository assignmentRepository;
    @Autowired
    SubjectService subjectService;
    @Autowired
    ISubjectRepository subjectRepository;

    @Override
    public List<Assignment> findAllAssignment(Integer pageno, Integer pagesize){
        Pageable pageable = PageRequest.of(pageno,pagesize);
        Page<Assignment> assignmentPage = assignmentRepository.findAll(pageable);
        List<Assignment> assignment = assignmentPage.getContent();
        return assignment;
    }
    @Override
    public Assignment getAssignmentById(int assignmentId){
        return (assignmentRepository.findById(assignmentId));
    }
    @Override
    public Assignment addAssignment(String title, String description, int subjectId, boolean status) {
        Assignment assignment = new Assignment();

        assignment.setTitle(title);
        assignment.setDescription(description);
        assignment.setSubject(subjectService.getSubjectById(subjectId));
        assignment.setStatus(status);
        assignment.setSubjectAssignment(true);

        assignmentRepository.save(assignment);
        return assignment;
    }
    @Override
    public List<Assignment> findAssignmentByManager(int subjectManagerId){
        return assignmentRepository.findAssignmentByManager(subjectManagerId);
    }
    @Override
    public Assignment saveAssignment(Assignment assignment){
        return  assignmentRepository.save(assignment);
    }
    @Override
    public boolean updateAssignment(int id, String title, String description, int subject, boolean status){
        Optional<Assignment> assignment = Optional.ofNullable(assignmentRepository.findById(id));

        if(assignment.isEmpty()) return false;

        Assignment assignmentData = assignment.get();
        assignmentData.setTitle(title);
        assignmentData.setDescription(description);
        assignmentData.setSubject(subjectRepository.findById(subject).get());
        assignmentData.setStatus(status);
        assignmentRepository.save(assignmentData);

        return true;
    }
}
