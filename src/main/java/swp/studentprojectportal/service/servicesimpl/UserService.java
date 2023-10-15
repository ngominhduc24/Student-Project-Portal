package swp.studentprojectportal.service.servicesimpl;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.ISettingRepository;
import swp.studentprojectportal.repository.IUserRepository;
import swp.studentprojectportal.service.IUserService;
import swp.studentprojectportal.utils.GooglePojo;
import swp.studentprojectportal.utils.Utility;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    IUserRepository userRepository;
    @Autowired
    ISettingRepository settingRepository;

    @Override
    public User saveUser(User user) {
        // TO-DO: set enable here
        return userRepository.save(user);
    }

    @Override
    public boolean checkEmailDomain(String email) {
        if(email == null) {
            return false;
        }
        String[] temp = email.split("@");
        if(temp.length == 0) return false;
        email = temp[temp.length-1];
        if (settingRepository.findSettingByTypeIdAndSettingTitle(2, email) != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkExistMail(String email) {
        if(userRepository.findUserByEmail(email) != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkExistPhoneNumber(String phoneNumber) {
        if(userRepository.findUserByPhone(phoneNumber) != null) {
            return true;
        }
        return false;
    }
    @Override
    public List<User> getUser(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> userPage = userRepository.findAll(pageable);
        List<User> users = userPage.getContent();
        return users;
    }

    public List<User> getUser(Integer pageNo, Integer pageSize, String search, Integer roleId) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        if(roleId != -1){
            return userRepository.searchUsersAndFilterByRole(search, roleId, pageable).getContent();
        } else {
            return userRepository.findUserByFullNameContainsIgnoreCaseOrEmailContainsIgnoreCaseOrPhoneContainsIgnoreCase(search, search, search, pageable).getContent();
        }
    }

    /*
     * pageNo is the index of page, start from 0
     * pageSize is the number of items in a page
     * search is the search term for searching name, email, phone
     * roleId is the role id of user
     * status is the status of user where 1 is active, 0 is inactive and -1 is all
     */

    public List<User> getUser(Integer pageNo, Integer pageSize, String search, Integer roleId, Integer status) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        if(status != -1 && roleId != -1) {
            return userRepository.searchUsersAndFilterByRoleIdAndStatus(search, roleId, status, pageable).getContent();
        } else if(status != -1) {
            return userRepository.searchUsersAndFilterByStatus(search, status, pageable).getContent();
        } else
        if(roleId != -1){
            return userRepository.searchUsersAndFilterByRole(search, roleId, pageable).getContent();
        } else {
            return userRepository.findUserByFullNameContainsIgnoreCaseOrEmailContainsIgnoreCaseOrPhoneContainsIgnoreCase(search, search, search, pageable).getContent();
        }
    }
    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAllUserByRoleId(int roleId) {
        return userRepository.findAllBySetting(settingRepository.findById(roleId).get());
    }

    @Override
    public boolean updateUserStatus(int id, boolean status) {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()) return false;

        user.get().setStatus(status);
        userRepository.save(user.get());
        return true;
    }

    @Override
    public boolean updateUser(int id, String fullName, String email, String phone, int roleId, boolean status, String note) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) return false;

        User userData = user.get();
        userData.setFullName(fullName);
        userData.setEmail(email);
        userData.setPhone(phone);
        userData.setSetting(settingRepository.findById(roleId).get());
        userData.setStatus(status);
        userData.setNote(note);

        userRepository.save(userData);
        return true;
    }

    @Override
    public User addUser(String fullName, String email, String phone, int roleId) {
        User user = new User();

        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setSetting(settingRepository.findById(roleId).get());

        userRepository.save(user);

        return user;
    }

    @Override
    public User findUserByEmailAndPassword(String username, String password) {
        try {
            return userRepository.findUserByEmailAndPassword(username, Utility.hash(password));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findUserByPhoneAndPassword(String username, String password) {
        try {
            return userRepository.findUserByPhoneAndPassword(username, Utility.hash(password));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        User user;
        try {
            if (username.contains("@"))
                    user = userRepository.findUserByEmailAndPassword(username, Utility.hash(password));
            else
                user = userRepository.findUserByPhoneAndPassword(username, Utility.hash(password));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User registerAccountFromGoogle(GooglePojo googlePojo) {
        String[] temp = googlePojo.getEmail().split("@");
        String fullName = temp[0];
        //save to account
        User newUser = new User();
        newUser.setFullName(fullName);
        newUser.setEmail(googlePojo.getEmail());
        try {
            newUser.setPassword(googlePojo.getId());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        newUser.setAvatarUrl(googlePojo.getPicture());
        newUser.setActive(true);
        newUser.setSetting(settingRepository.findById(1).get());
        User user = userRepository.save(newUser);
        return user;
    }
    public void setCookie(Cookie cu, Cookie cp, Cookie cr, String remember) {
        if(remember!=null){
            cu.setMaxAge(60*60*24*7);
            cp.setMaxAge(60*60*24*7);
            cr.setMaxAge(60*60*24*7);
        } else {
            cu.setMaxAge(0);
            cp.setMaxAge(0);
            cr.setMaxAge(0);
        }
    }
    @Override
    // get totalPage
    public int getTotalPage(int pageSize) {
        long count = userRepository.count();
        int totalPage = count % pageSize == 0 ? (int) (count / pageSize) : (int) (count / pageSize) + 1;
        return totalPage;
    }

    @Override
    public int getTotalPage(int pageSize, int roleId) {
        long count = userRepository.countAllBySettingId(roleId);
        int totalPage = count % pageSize == 0 ? (int) (count / pageSize) : (int) (count / pageSize) + 1;
        return totalPage;
    }

    @Override
    public List<User> findTeacherBySubjectManagerId(int subjectManagerId) {
        return userRepository.findTeacherBySubjectManagerId(subjectManagerId);
    }

    @Override
    public List<User> findTeacherByRoleIdAndStatus(Integer roleId, Boolean status) {
        return userRepository.findTeacherBySettingIdAndStatus(roleId, status);
    }

    @Override
    public User resetPasswordByToken(String token) {
        User user = userRepository.findUserByToken(token);
        if(user != null) {
            user.setToken(null);
            user.setActive(true);
            try {
                user.setPassword("");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            userRepository.save(user);
        }
        return user;
    }

    @Override
    public User getUserByEmailOrPhone(String userName) {
        userName = userName.replace(" ", "").replace("+84", "0");
        return userRepository.findUserByEmailOrPhone(userName, userName);
    }

    @Override
    public List<User> findAllProjectMentor() {
        return userRepository.findAllBySettingIdOrSettingId(3,4);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.getById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findByPhone(String phone) {
        return userRepository.findUserByPhone(phone);
    }

}
