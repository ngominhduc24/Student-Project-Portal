package swp.studentprojectportal.services;

import swp.studentprojectportal.model.User;

public interface IUserService {
    User registerNewAccount(User user);
    boolean checkEmailDomain(String email);
    boolean checkExistMail(String email);
    boolean checkExistPhoneNumber(String phoneNumber);
    public boolean saveUser(User user);

    User findUserByEmailAndPassword(String username, String password);
    User findUserByPhoneAndPassword(String username, String password);
}
