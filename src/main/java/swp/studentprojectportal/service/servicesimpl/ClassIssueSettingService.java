package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.ClassIssueSetting;
import swp.studentprojectportal.repository.IClassIssueSettingRepository;
import swp.studentprojectportal.service.IClassIssueSettingService;

import java.util.List;

@Service
public class ClassIssueSettingService implements IClassIssueSettingService {
    @Autowired
    IClassIssueSettingRepository classIssueSettingRepository;

    @Override
    public ClassIssueSetting findById(int id){
        return classIssueSettingRepository.findById(id).get();
    }

    @Override
    public ClassIssueSetting saveClassIssueSetting(ClassIssueSetting classIssueSetting){
        return classIssueSettingRepository.save(classIssueSetting);
    }

    @Override
    public Page<ClassIssueSetting> findAllByClassManagerId(int teacherId, String search, Integer pageNo,
                                               Integer pageSize, String sortBy, Integer sortType, Integer classId,
                                               Integer status){
        Sort sort;
        if(sortType==1)
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();
        return classIssueSettingRepository.filterByClassManager(teacherId,search,classId,status,PageRequest.of(pageNo, pageSize, sort));
    }

    @Override
    public List<ClassIssueSetting> getALL(int teacherId){
        return classIssueSettingRepository.getAll(teacherId);
    }
}
