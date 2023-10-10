package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.ClassIssueSetting;
import swp.studentprojectportal.repository.IClassIssueSettingRepository;
import swp.studentprojectportal.service.IClassIssueSettingService;
@Service
public class ClassIssueSettingService implements IClassIssueSettingService {
    @Autowired
    IClassIssueSettingRepository classIssueSettingRepository;

    @Override
    public Page<ClassIssueSetting> filter(int teacherId, String search, Integer pageNo, Integer pageSize,
                                   String sortBy, Integer sortType, Integer status){
        Sort sort;
        if(sortType==1)
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();
        return classIssueSettingRepository.filter(teacherId,search,status, PageRequest.of(pageNo, pageSize, sort));
    }
}
