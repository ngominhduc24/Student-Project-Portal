package swp.studentprojectportal.services.servicesimpl;

import swp.studentprojectportal.model.User;

public interface IUserService {
    User findUserByEmailAndPassword(String username, String password);
    User findUserByPhoneAndPassword(String username, String password);
}
