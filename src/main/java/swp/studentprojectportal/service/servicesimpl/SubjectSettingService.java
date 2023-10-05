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
    public List<SubjectSetting> filterSubjectSettingBySubjectAndTypeAndStatus(int subjectManagerId, int subjectId, int typeId, int status) {
        List<SubjectSetting> result = new ArrayList<>();
        if(subjectId!=-1){
              Subject subject = subjectRepository.getById(subjectId);
              if(typeId!=-1 && status!=-1)
                  result = subjectSettingRepository.findSubjectSettingBySubjectAndTypeIdAndStatus(subject, typeId, status==1);
              else if (typeId!=-1 && status==-1)
                  result = subjectSettingRepository.findSubjectSettingBySubjectAndTypeId(subject ,typeId);
              else if (typeId==-1 && status!=-1)
                  result = subjectSettingRepository.findSubjectSettingBySubjectAndStatus(subject, status==1);
              else
                  result = subjectSettingRepository.findSubjectSettingBySubject(subject);
        } else {
              if(typeId!=-1 && status != -1)
                  result = subjectSettingRepository.findSubjectSettingByManagerAndTypeIdAndStatus(subjectManagerId, typeId, status);
              else if (typeId!=-1 && status==-1)
                  result = subjectSettingRepository.findSubjectSettingByManagerAndTypeId(subjectManagerId, typeId);
              else if (typeId==-1 && status!=-1)
                  result = subjectSettingRepository.findSubjectSettingByManagerAndStatus(subjectManagerId, status);
              else
                  result = subjectSettingRepository.findSubjectSettingByManager(subjectManagerId);
        }
        return result;
    }

    @Override
    public SubjectSetting saveSubjectSetting(SubjectSetting subjectSetting) {
        return subjectSettingRepository.save(subjectSetting);
    }

    public SubjectSetting findById(int id){
        return subjectSettingRepository.findById(id).get();
    }
}
