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
    public List<Setting> getAllRole() {
        return settingRepository.findSettingByTypeId(1);
    }
}
