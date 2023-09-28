package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Setting> findSettingByTypeIdOrderByDisplayOrder(Integer typeId) {
        return settingRepository.findSettingByTypeIdOrderByDisplayOrder(typeId);
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
    public boolean checkExistedSettingTitle(String settingTitle, String id) {
        Setting setting = settingRepository.findBySettingTitle(settingTitle);
        if(setting !=null)  {
            if (id==null || id.isEmpty())    return true;
            if(setting.getId()!=Integer.parseInt(id))  return true;
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
        if(typeId==1) typeName="Role";
        if(typeId==3) typeName="Semester";
        if(typeId==2) typeName="Perrmitted Email Domain";
        return typeName;
    }

    public Setting findLastDisplayOrder(int typeId){
        return settingRepository.findTop1SettingByTypeIdOrderByDisplayOrderDesc(typeId);
    }
}
