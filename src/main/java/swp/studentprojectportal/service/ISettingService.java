package swp.studentprojectportal.service;

import swp.studentprojectportal.model.Setting;

import java.util.List;

public interface ISettingService {
    List<Setting> findSettingByTypeIdOrderByDisplayOrder(Integer typeId);
    Setting getSettingByID(Integer settingId);
    Setting saveSetting(Setting setting);
    List<Setting> getAllRole();
    Setting getLastestSemester();
    Setting findById(int id);
    Setting findBySettingTitle(String settingTitle);
    Setting findByTypeIdAndDisplayOrder(int typeId, int displayOrder);
}
