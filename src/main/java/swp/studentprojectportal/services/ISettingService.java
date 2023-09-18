package swp.studentprojectportal.services;

import swp.studentprojectportal.model.Setting;

import java.util.List;

public interface ISettingService {
    List<Setting> findSettingByTypeIdOrderByDisplayOrder(Integer typeId);
    Setting getSettingByID(Integer settingId);
    Setting saveSetting(Setting setting);
    List<Setting> getAllRole();

    Setting findById(int id);
}
