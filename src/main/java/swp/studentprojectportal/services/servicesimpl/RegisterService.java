package swp.studentprojectportal.services.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.IUserRepository;
import swp.studentprojectportal.services.IRegisterService;

@Service
public class RegisterService implements IRegisterService {
    @Autowired
    IUserRepository userRepository;
    @Override
    public User verifyToken(String token) {
        User user = userRepository.findUserByToken(token);
        if(user != null) {
            user.setToken(null);
            user.setActive(true);
            userRepository.save(user);
        }
        return user;
    }
}
