package swp.studentprojectportal.service;

import org.springframework.data.domain.Page;
import swp.studentprojectportal.model.IssueSetting;

import java.util.List;

public interface IIssueSettingService {
    List<IssueSetting> findSubjectSettingByManager(int subjectManagerId);
    Page<IssueSetting> filter(Integer subjectId, String search, Integer pageNo, Integer pageSize,
                              String sortBy, Integer sortType, String settingGroup, Integer status);
    Page<IssueSetting> filterClassIssueSetting(Integer subjectId, Integer classId, String search, Integer pageNo, Integer pageSize,
                                               String sortBy, Integer sortType, String settingGroup, Integer status);

    IssueSetting saveSubjectSetting(IssueSetting setting);
    IssueSetting findById(int id);

    IssueSetting findByClassAndGroupAndTitle(int classId,String settingGroup, String settingTitle);

    IssueSetting findBySubjectAndGroupAndTitle(int subjectId,String settingGroup, String settingTitle);

    List<String> findAllDistinctSettingGroup(Integer subjectId);
    List<String> findAllDistinctClassSettingGroup(Integer subjectId, Integer classId);
    List<String> findAllDistinctProjectSettingGroup(Integer subjectId, Integer classId, Integer projectId);
    Page<IssueSetting> filterProjectIssueSetting(Integer subjectId, Integer classId, Integer projectId, String search, Integer pageNo, Integer pageSize,
                                               String sortBy, Integer sortType, String settingGroup, Integer status);
}
