package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Assignment;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.Milestone;
import swp.studentprojectportal.repository.IAssignmentRepository;
import swp.studentprojectportal.repository.IMilestoneRepository;
import swp.studentprojectportal.service.IMilestoneService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MilestoneService implements IMilestoneService {
    @Autowired
    IMilestoneRepository milestoneRepository;
    @Autowired
    IAssignmentRepository assignmentRepository;

    @Override
    public Page<Milestone> filterMilestone(int classId, String search, Integer pageNo, Integer pageSize, String sortBy, Integer sortType, Integer status) {
        Sort sort;
        if(sortType==1)
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();
        return milestoneRepository.filterClassBySubjectManager(classId, search, status, PageRequest.of(pageNo, pageSize, sort));
    }

    @Override
    public Milestone findMilestoneById(Integer id){
        return milestoneRepository.findMilestoneById(id);
    }

    @Override
    public Milestone save(Milestone milestone) {
        return milestoneRepository.save(milestone);
    }

    @Override
    public void addClassAssignment(Class classA) {
        List<Assignment> assignmentList = assignmentRepository.findAssignmentBySubjectId(classA.getSubject().getId());
        for (Assignment ass : assignmentList) {
            Milestone assign = new Milestone();
            assign.setTitle(ass.getTitle());
            assign.setDescription(ass.getDescription());
            assign.setAclass(classA);
            save(assign);
        }
    }

    @Override
    public boolean addNewMilestone(Integer classId, Integer subjectId, String title, String description, LocalDateTime startDate,LocalDateTime endDate,int status) {
        Milestone milestone = new Milestone();
        return true;
    }

}
