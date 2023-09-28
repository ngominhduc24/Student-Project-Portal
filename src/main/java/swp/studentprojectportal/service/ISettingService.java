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
    boolean checkExistedSettingTitle(String settingTitle, String id);
    boolean checkExistedDisplayOrder(int typeId, int displayOrder, String id);
    String setTypeName(int typeId);

    Setting findLastDisplayOrder(int typeId);

}
