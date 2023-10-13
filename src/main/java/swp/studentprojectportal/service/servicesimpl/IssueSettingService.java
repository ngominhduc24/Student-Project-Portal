package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.IssueSetting;
import swp.studentprojectportal.repository.ISubjectRepository;
import swp.studentprojectportal.repository.IIssueSettingRepository;
import swp.studentprojectportal.service.IIssueSettingService;

import java.util.List;
@Service
public class IssueSettingService implements IIssueSettingService {
    @Autowired
    IIssueSettingRepository issueSettingRepository;
    @Autowired
    ISubjectRepository subjectRepository;

    @Override
    public List<IssueSetting> findSubjectSettingByManager(int subjectManagerId) {
        return issueSettingRepository.findSubjectSettingByManager(subjectManagerId);
    }

    @Override
    public Page<IssueSetting> filter(Integer subjectId, String search, Integer pageNo, Integer pageSize,
                                     String sortBy, Integer sortType, String settingGroup, Integer status) {
        Sort sort;
        if(sortType==1)
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();
        return issueSettingRepository.filter(subjectId, search, settingGroup, status, PageRequest.of(pageNo, pageSize, sort));
    }

    @Override
    public IssueSetting saveSubjectSetting(IssueSetting issueSetting) {
        return issueSettingRepository.save(issueSetting);
    }

    public IssueSetting findById(int id){
        return issueSettingRepository.findById(id).get();
    }

    @Override
    public List<String> findAllDistinctSettingGroup(Integer subjectId) {
        return issueSettingRepository.findAllDistinctSettingGroup(subjectId);
    }
}
