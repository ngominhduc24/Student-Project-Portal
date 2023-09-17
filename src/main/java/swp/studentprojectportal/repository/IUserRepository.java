package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Setting;
import swp.studentprojectportal.model.User;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    User findUserByEmailAndPassword(String username,String password);
    User findUserByPhoneAndPassword(String username,String password);
    User findUserByToken(String token);
    User findUserByEmail(String email);
    User findUserByPhone(String phone);

    List<User> findAllBySetting(Setting setting);
}
