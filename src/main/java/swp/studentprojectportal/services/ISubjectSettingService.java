package swp.studentprojectportal.services;

import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.SubjectSetting;

import java.util.List;

public interface ISubjectSettingService {
    List<SubjectSetting> findSubjectSettingBySubjectAndTypeIdOrderByDisplayOrder(Subject subject, Integer typeId);
    SubjectSetting saveSubjectSetting(SubjectSetting setting);
    SubjectSetting findById(int id);
}
