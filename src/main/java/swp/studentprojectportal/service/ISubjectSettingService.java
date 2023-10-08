package swp.studentprojectportal.service;

import org.springframework.data.domain.Page;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.SubjectSetting;

import java.util.List;

public interface ISubjectSettingService {
    List<SubjectSetting> findSubjectSettingByManager(int subjectManagerId);
    Page<SubjectSetting> filter(int subjectManagerId,String search, Integer pageNo, Integer pageSize,
                                String sortBy, Integer sortType, Integer subjectId, Integer typeId, Integer status);

    SubjectSetting saveSubjectSetting(SubjectSetting setting);
    SubjectSetting findById(int id);
}
