package swp.studentprojectportal.services.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.IUserRepository;
import swp.studentprojectportal.services.IResetPassword;

@Service
public class ResetPassword implements IResetPassword {
    @Autowired
    IUserRepository userRepository;
    @Override
    public User resetPasswordByToken(String token) {
        User user = userRepository.findUserByToken(token);
        if(user != null) {
            user.setToken(null);
            user.setPassword("");
            userRepository.save(user);
        }
        return user;
    }

    @Override
    public User getUserByEmailOrPhone(String userName) {
        return userRepository.findUserByEmailOrPhone(userName, userName);
    }
}
