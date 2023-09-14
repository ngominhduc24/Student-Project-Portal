package swp.studentprojectportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    User findUserByEmailAndPassword(String username,String password);
    User findUserByPhoneAndPassword(String username,String password);
}
