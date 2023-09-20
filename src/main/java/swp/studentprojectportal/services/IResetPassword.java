package swp.studentprojectportal.services;

import swp.studentprojectportal.model.User;

public interface IResetPassword {
    User resetPasswordByToken(String token);
    User getUserByEmailOrPhone(String userName);
}
