package swp.studentprojectportal.services;

import swp.studentprojectportal.model.User;

public interface IUserServices {
    User registerNewAccount(User user);
    boolean checkEmailDomain(String email);
    boolean checkExistMail(String email);
    boolean checkExistPhoneNumber(String phoneNumber);
}
