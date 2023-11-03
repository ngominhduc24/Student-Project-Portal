package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.SubjectSetting;
import swp.studentprojectportal.repository.ISubjectRepository;
import swp.studentprojectportal.repository.ISubjectSettingRepository;
import swp.studentprojectportal.service.ISubjectSettingService;

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
    public Page<SubjectSetting> filter(int subjectManagerId, String search, Integer pageNo, Integer pageSize,
                                       String sortBy, Integer sortType, Integer subjectId, Integer typeId, Integer status) {
        Sort sort;
        if(sortType==1)
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();
        return subjectSettingRepository.filter(subjectManagerId, search, subjectId ,typeId, status, PageRequest.of(pageNo, pageSize, sort));
    }

    @Override
    public SubjectSetting saveSubjectSetting(SubjectSetting subjectSetting) {
        return subjectSettingRepository.save(subjectSetting);
    }

    public SubjectSetting findById(int id){
        return subjectSettingRepository.findById(id).get();
    }
}
