package swp.studentprojectportal.service;

import org.springframework.data.domain.Page;
import swp.studentprojectportal.model.Setting;

import java.util.List;

public interface ISettingService {
    Page<Setting> filter(String search, Integer pageNo, Integer pageSize,
                          String sortBy, Integer sortType, Integer typeId, Integer status);
    Setting getSettingByID(Integer settingId);
    Setting saveSetting(Setting setting);
    List<Setting> getAllRole();
    Setting getLastestSemester();
    Setting findById(int id);
    boolean checkExistedSettingTitle(String settingTitle, String id);
    boolean checkExistedDisplayOrder(int typeId, int displayOrder, String id);
    String setTypeName(int typeId);
    Setting findLastDisplayOrder(int typeId);
    List<Setting> findSemesterBySubjectManagerId(int subjectManagerId);
    List<Setting> findSemesterByClassManagerId(int classManagerId);

}
