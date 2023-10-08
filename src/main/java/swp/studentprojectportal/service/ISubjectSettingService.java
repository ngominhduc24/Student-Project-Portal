package swp.studentprojectportal.service;

import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.SubjectSetting;

import java.util.List;

public interface ISubjectSettingService {
    List<SubjectSetting> findSubjectSettingByManager(int subjectManagerId);
    List<SubjectSetting> filter(int subjectManagerId, Integer subjectId, Integer typeId, Integer status);

    SubjectSetting saveSubjectSetting(SubjectSetting setting);
    SubjectSetting findById(int id);
}
