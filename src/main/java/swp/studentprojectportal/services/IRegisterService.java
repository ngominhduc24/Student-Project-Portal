package swp.studentprojectportal.services;

import swp.studentprojectportal.model.User;

public interface IRegisterService {
    User verifyToken(String token);
}
