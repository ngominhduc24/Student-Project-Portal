package swp.studentprojectportal.services.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.ISettingRepository;
import swp.studentprojectportal.repository.IUserRepository;
import swp.studentprojectportal.services.IUserService;
import swp.studentprojectportal.repository.IUserRepository;
import swp.studentprojectportal.services.IUserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    IUserRepository userRepository;
    @Autowired
    ISettingRepository settingRepository;

    @Override
    public User registerNewAccount(User user) {
        // TO-DO: set enable here
        return userRepository.save(user);
    }

    @Override
    public boolean checkEmailDomain(String email) {
        String[] temp = email.split("@");
        if(temp.length == 0) return false;
        email = temp[temp.length-1];
        if (settingRepository.findSettingByTypeIdAndSettingTitle(2, email) != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkExistMail(String email) {
        if(userRepository.findUserByEmail(email) != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkExistPhoneNumber(String phoneNumber) {
        if(userRepository.findUserByPhone(phoneNumber) != null) {
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean updateUserStatus(int id, boolean status) {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()) return false;

        user.get().setStatus(status);
        userRepository.save(user.get());
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
