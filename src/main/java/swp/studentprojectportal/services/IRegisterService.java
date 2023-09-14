package swp.studentprojectportal.services;

import swp.studentprojectportal.model.User;

public interface IRegisterService {
    boolean verifyToken(String token);
}
