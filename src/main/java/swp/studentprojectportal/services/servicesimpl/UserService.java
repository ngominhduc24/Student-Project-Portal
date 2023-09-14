package swp.studentprojectportal.services.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.IUserRepository;
import swp.studentprojectportal.services.IUserService;

import java.util.List;
import java.util.Optional;

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
}
