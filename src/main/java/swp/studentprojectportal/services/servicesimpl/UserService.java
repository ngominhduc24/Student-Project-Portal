package swp.studentprojectportal.services.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.IUserRepository;
@Service
public class UserService implements IUserService {
    @Autowired
    IUserRepository userRepository;

    @Override
    public User findUserByEmailAndPassword(String username, String password) {
        return userRepository.findUserByEmailAndPassword(username, password);
    }

    @Override
    public User findUserByPhoneAndPassword(String username, String password) {
        return userRepository.findUserByPhoneAndPassword(username, password);
    }
}
