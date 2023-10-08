package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.SubjectSetting;
import swp.studentprojectportal.repository.ISubjectRepository;
import swp.studentprojectportal.repository.ISubjectSettingRepository;
import swp.studentprojectportal.service.ISubjectSettingService;

import java.util.ArrayList;
import java.util.List;
@Service
public class SubjectSettingService implements ISubjectSettingService {
    @Autowired
    ISubjectSettingRepository subjectSettingRepository;
    @Autowired
    ISubjectRepository subjectRepository;

    @Override
    public List<SubjectSetting> findSubjectSettingByManager(int subjectManagerId) {
        return subjectSettingRepository.findSubjectSettingByManager(subjectManagerId);
    }

    @Override
    public List<SubjectSetting> filter(int subjectManagerId, Integer subjectId, Integer typeId, Integer status) {
        return subjectSettingRepository.filter(subjectManagerId, subjectId ,typeId, status);
    }

    @Override
    public SubjectSetting saveSubjectSetting(SubjectSetting subjectSetting) {
        return subjectSettingRepository.save(subjectSetting);
    }

    public SubjectSetting findById(int id){
        return subjectSettingRepository.findById(id).get();
    }
}
