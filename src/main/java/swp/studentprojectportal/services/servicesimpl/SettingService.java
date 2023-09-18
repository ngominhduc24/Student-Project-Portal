package swp.studentprojectportal.services.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Setting;
import swp.studentprojectportal.repository.ISettingRepository;
import swp.studentprojectportal.services.ISettingService;

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
        return settingRepository.findSettingByTypeId(1);
    }

    @Override
    public Setting findById(int id) {
        return settingRepository.findById(id).get();
    }
}
