package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Setting;
import swp.studentprojectportal.repository.ISettingRepository;
import swp.studentprojectportal.service.ISettingService;

import java.util.List;

@Service
public class SettingService implements ISettingService {
    @Autowired
    ISettingRepository settingRepository;


    @Override
    public Page<Setting> filter(String search, Integer pageNo, Integer pageSize,
                                 String sortBy, Integer sortType, Integer typeId, Integer status) {
        Sort sort;
        if(sortType==1)
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();
        return settingRepository.filter(search, typeId, status, PageRequest.of(pageNo, pageSize, sort));
    }

    @Override
    public Setting getSettingByID(Integer id) {
        return settingRepository.findById(id).orElse(null);
    }

    @Override
    public Setting saveSetting(Setting setting) {
        return settingRepository.save(setting);
    }

    public List<Setting> getAllRole() {
        //type 1 = role;
        return settingRepository.findSettingByTypeIdOrderByDisplayOrder(1);
    }

    @Override
    public Setting getLastestSemester() {
        //type 3 = semester;
        List<Setting> list = settingRepository.findSettingByTypeIdOrderByDisplayOrder(3);
        return list.get(list.size()-1);
    }

    @Override
    public Setting findById(int id) {
        return settingRepository.findById(id).get();
    }

    @Override
    public boolean checkExistedSettingTitle(String settingTitle, Integer id) {
        Setting setting = settingRepository.findBySettingTitle(settingTitle);
        if(setting !=null)  {
            if(settingTitle.equals(setting.getSettingTitle()) && id != setting.getId())
                return true;
        }
        return false;
    }

    @Override
    public boolean checkExistedDisplayOrder(int typeId, int displayOrder, String id) {
        Setting setting = settingRepository.findByTypeIdAndDisplayOrder(typeId, displayOrder);
        if(setting !=null)  {
            if (id==null || id.isEmpty())    return true;
            if(setting.getId()!=Integer.parseInt(id))  return true;
        }
        return false;
    }

    @Override
    public String setTypeName(int typeId) {
        String typeName="";
        if(typeId==1) typeName="User Role";
        if(typeId==3) typeName="Semester";
        if(typeId==2) typeName="Perrmitted Email Domain";
        return typeName;
    }

    public Setting findLastDisplayOrder(int typeId){
        return settingRepository.findTop1SettingByTypeIdOrderByDisplayOrderDesc(typeId);
    }

    @Override
    public List<Setting> findSemesterBySubjectManagerId(int subjectManagerId) {
        return settingRepository.findSemesterBySubjectManagerId(subjectManagerId);
    }

    @Override
    public List<Setting> findSemesterByClassManagerId(int classManagerId) {
        return settingRepository.findSemesterByClassManagerId(classManagerId);
    }

    @Override
    public List<Setting> findSemesterByStatus(Integer typeId, Boolean status) {
        return settingRepository.findSemesterByTypeIdAndStatus(typeId, status);
    }
}
