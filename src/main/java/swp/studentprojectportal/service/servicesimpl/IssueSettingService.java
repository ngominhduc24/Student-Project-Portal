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
    public Page<IssueSetting> filterClassIssueSetting(Integer subjectId, Integer classId, String search, Integer pageNo,
                                                      Integer pageSize, String sortBy, Integer sortType, String settingGroup, Integer status) {
        Sort sort;
        if(sortType==1)
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();
        return issueSettingRepository.filterClassIssueSetting(subjectId, classId, search, settingGroup, status, PageRequest.of(pageNo, pageSize, sort));
    }

    @Override
    public IssueSetting saveIssueSetting(IssueSetting issueSetting) {
        return issueSettingRepository.save(issueSetting);
    }

    public IssueSetting findById(int id){
        IssueSetting setting = new IssueSetting();
        if(id == -1) {
            setting.setId(-1);
            setting.setSettingTitle("");
        } else {
            setting = issueSettingRepository.findById(id).get();
        }
        return setting;
    }

    @Override
    public List<String> findAllDistinctSettingGroup(Integer subjectId) {
        return issueSettingRepository.findAllDistinctSettingGroup(subjectId);
    }

    @Override
    public List<IssueSetting> findProcessTitle(Integer subjectId, String type) {
        return issueSettingRepository.findProcessTitle(subjectId, type);
    }

    @Override
    public List<String> findAllDistinctClassSettingGroup(Integer subjectId, Integer classId) {
        return issueSettingRepository.findAllDistinctClassSettingGroup(subjectId, classId);
    }

    @Override
    public List<String> findAllDistinctProjectSettingGroup(Integer subjectId, Integer classId, Integer projectId) {
        return issueSettingRepository.findAllDistinctProjectSettingGroup(subjectId, classId, projectId);
    }

    @Override
    public Page<IssueSetting> filterProjectIssueSetting(Integer subjectId, Integer classId, Integer projectId, String search, Integer pageNo, Integer pageSize, String sortBy, Integer sortType, String settingGroup, Integer status) {
        Sort sort;
        if(sortType==1)
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();
        return issueSettingRepository.filterProjectIssueSetting(subjectId, classId,projectId, search, settingGroup, status, PageRequest.of(pageNo, pageSize, sort));
    }

    @Override
    public IssueSetting findByClassAndGroupAndTitle(int classId,String settingGroup, String settingTitle){
        return issueSettingRepository.findIssueSettingByAclassAndSettingGroupAndSettingTitle(classId, settingGroup, settingTitle).orElse(null);
    }

    @Override
    public IssueSetting findBySubjectAndGroupAndTitle(int subjectId,String settingGroup, String settingTitle){
        return issueSettingRepository.findIssueSettingBySubjectAndSettingGroupAndSettingTitle(subjectId, settingGroup, settingTitle).orElse(null);
    }

    public List<IssueSetting> findAllSettingServiceByClassId(int classId){
        return issueSettingRepository.findAllByAclass_Id(classId);
    }

    public List<IssueSetting> findAllSettingServiceByProjectId(int projectId){
        return issueSettingRepository.findAllByProjectId(projectId);
    }
}
