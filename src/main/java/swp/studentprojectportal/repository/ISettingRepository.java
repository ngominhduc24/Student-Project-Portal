package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Setting;

@Repository
public interface ISettingRepository extends JpaRepository<Setting, Integer> {
}
