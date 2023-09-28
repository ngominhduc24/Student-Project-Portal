package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Setting;

import java.util.List;

@Repository
public interface ISettingRepository extends JpaRepository<Setting, Integer> {
    Setting findSettingByTypeIdAndSettingTitle(Integer typeId, String settingTitle);
    List<Setting> findSettingByTypeIdOrderByDisplayOrder(Integer typeId);
    Setting findBySettingTitle(String settingTitle);
    Setting findByTypeIdAndDisplayOrder(int typeId, int displayOrder);
    Setting findTop1SettingByTypeIdOrderByDisplayOrderDesc(int typeId);
}
