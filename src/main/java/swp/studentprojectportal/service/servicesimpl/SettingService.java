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
        return settingRepository.findSettingByTypeId(1);
    }

    @Override
    public Setting getLastestSemester() {
        //type 3 = semester;
        List<Setting> list = settingRepository.findSettingByTypeId(3);
        return list.get(list.size()-1);
    }

    @Override
    public Setting findById(int id) {
        return settingRepository.findById(id).get();
    }

    @Override
    public Setting findBySettingTitle(String settingTitle) {
        return settingRepository.findBySettingTitle(settingTitle);
    }

    @Override
    public Setting findByTypeIdAndDisplayOrder(int typeId, int displayOrder) {
        return settingRepository.findByTypeIdAndDisplayOrder(typeId, displayOrder);
    }
}
