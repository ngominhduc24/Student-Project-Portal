package swp.studentprojectportal.services.servicesimpl;

import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.services.IUserServices;

@Service
public class UserServices implements IUserServices {
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
        return false;
    }

    @Override
    public boolean checkExistPhoneNumber(String phoneNumber) {
        return false;
    }
}
