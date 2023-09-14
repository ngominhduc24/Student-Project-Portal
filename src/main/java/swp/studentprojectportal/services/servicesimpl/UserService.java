package swp.studentprojectportal.services.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.IUserRepository;
import swp.studentprojectportal.services.IUserService;

@Service
public class UserService implements IUserService {
    @Autowired
    IUserRepository userRepository;
    @Override
    public User registerNewAccount(User user) {
        System.out.println(user);
        return null;
    }

    @Override
    public boolean checkEmailDomain(String email) {
        return true;
    }

    @Override
    public boolean checkExistMail(String email) {
        return true;
    }

    @Override
    public boolean checkExistPhoneNumber(String phoneNumber) {
        return true;
    }

    @Override
    public User saveUserWaitVerify(User user) {
        return userRepository.save(user);
    }
    @Override
    public User findUserByEmailAndPassword(String username, String password) {
        return userRepository.findUserByEmailAndPassword(username, password);
    }

    @Override
    public User findUserByPhoneAndPassword(String username, String password) {
        return userRepository.findUserByPhoneAndPassword(username, password);
    }
}
