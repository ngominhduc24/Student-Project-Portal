package swp.studentprojectportal.service;

import swp.studentprojectportal.model.User;

public interface IRegisterService {
    User verifyToken(String token);
}
