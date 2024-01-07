package swp.studentprojectportal.service;

import swp.studentprojectportal.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User saveUser(User user);

    int countStudent();

    int countTeacher();

    boolean checkEmailDomain(String email);
    boolean checkExistMail(String email);
    boolean checkExistPhoneNumber(String phoneNumber);
    User findUserByEmailAndPassword(String username, String password);
    User findUserByPhoneAndPassword(String username, String password);
    List<User> getUser(Integer pageNo, Integer pageSize);
    List<User> findAllUser();
    Optional<User> findUserById(int id);
    List<User> findAllUserByRoleId(int roleId);
    boolean updateUserStatus(int id, boolean status);
    boolean updateUser(int id, String fullName, String email, String phone, int roleId, boolean status, String note);

    boolean updateStudent(int id, boolean status, String note);

    User addUser(String fullName, String email, String phone, int roleId);
    User findUserByUsernameAndPassword(String username, String password);
    int getTotalPage(int pageSize);
    User resetPasswordByToken(String token);
    User getUserByEmailOrPhone(String userName);
    List<User> findAllProjectMentor();
    int getTotalPage(int pageSize, int roleId);
    List<User> findTeacherBySubjectManagerId(int subjectManagerId);
    List<User> findTeacherByRoleIdAndStatus(Integer roleId, Boolean status);
    User getUserById(int id);

    User findByEmail(String email);

    User findByPhone(String phone);
}
