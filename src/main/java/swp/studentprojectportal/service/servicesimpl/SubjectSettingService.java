package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.SubjectSetting;
import swp.studentprojectportal.repository.ISubjectSettingRepository;
import swp.studentprojectportal.service.ISubjectSettingService;

import java.util.List;
@Service
public class SubjectSettingService implements ISubjectSettingService {
    @Autowired
    ISubjectSettingRepository subjectSettingRepository;

    @Override
    public List<SubjectSetting> findSubjectSettingBySubjectAndTypeIdOrderByDisplayOrder(Subject subject, Integer typeId) {
        return subjectSettingRepository.findSubjectSettingBySubjectAndTypeIdOrderByDisplayOrder(subject, typeId);
    }

    @Override
    public SubjectSetting saveSubjectSetting(SubjectSetting subjectSetting) {
        return subjectSettingRepository.save(subjectSetting);
    }


    public SubjectSetting findById(int id){
        return subjectSettingRepository.findById(id).get();
    }
}
