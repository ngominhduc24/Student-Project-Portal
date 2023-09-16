package swp.studentprojectportal.services;

import swp.studentprojectportal.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User registerNewAccount(User user);
    boolean checkEmailDomain(String email);
    boolean checkExistMail(String email);
    boolean checkExistPhoneNumber(String phoneNumber);
    public User saveUserWaitVerify(User user);
    User findUserByEmailAndPassword(String username, String password);
    User findUserByPhoneAndPassword(String username, String password);
    List<User> getAllUser();
    Optional<User> getUserById(int id);
    boolean updateUserStatus(int id, boolean status);

    boolean updateUser(int id, String fullName, String email, String phone, int roleId, boolean status);
}
