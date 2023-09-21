package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Setting;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.SubjectSetting;

import java.util.List;
@Repository
public interface ISubjectSettingRepository extends JpaRepository<SubjectSetting, Integer> {
    List<SubjectSetting> findSubjectSettingBySubjectAndTypeIdOrderByDisplayOrder(Subject subject, Integer typeId);
}
