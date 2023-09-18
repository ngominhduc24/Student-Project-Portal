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
    public User saveUser(User user) {
        // TO-DO: set enable here
        return userRepository.save(user);
    }

    @Override
    public boolean checkEmailDomain(String email) {
        if(email == null) {
            return false;
        }
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
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAllUserByRoleId(int roleId) {
        return userRepository.findAllBySetting(settingRepository.findById(roleId).get());
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
    public boolean updateUser(int id, String fullName, String email, String phone, int roleId, boolean status, String note) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) return false;

        User userData = user.get();
        userData.setFullName(fullName);
        userData.setEmail(email);
        userData.setPhone(phone);
        userData.setSetting(settingRepository.findById(roleId).get());
        userData.setStatus(status);
        userData.setNote(note);

        userRepository.save(userData);
        return true;
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
