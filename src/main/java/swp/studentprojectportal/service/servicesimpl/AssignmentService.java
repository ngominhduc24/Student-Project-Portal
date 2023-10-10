package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<Assignment> filter(int subjectManagerId, String search, Integer pageNo, Integer pageSize,
                            String sortBy, Integer sortType, Integer subjectId, Integer status){
        Sort sort;
        if(sortType==1)
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();
        return assignmentRepository.filter(subjectManagerId, search, subjectId, status, PageRequest.of(pageNo, pageSize, sort));
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
