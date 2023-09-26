package swp.studentprojectportal.utils;

import org.springframework.beans.factory.annotation.Autowired;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.SubjectSevice;
import swp.studentprojectportal.service.servicesimpl.UserService;

public class Validate {
    /*
     * Function to validate email
     * Email must contain @ and .
     */
    static SubjectSevice subjectService;
    static UserService userService;

    public static boolean validEmail(String email) {
        if(email == null) return false;
        if(email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) return true;
        return false;
    }

    /*
     * Function to validate phone number
     * Convert +84 to 0 before validate
     * Phone number must start with 0 and have 10 digits
     */
    public static boolean validPhoneNumber(String phoneNumber) {
        if(phoneNumber == null) return false;
        phoneNumber = phoneNumber.replace("+84", "0");
       // if(phoneNumber.matches("(0[3|5|7|8|9])+([0-9]{8})\\b")) return true;
        if(phoneNumber.matches("(0[0-9])+([0-9]{8})\\b")) return true;
       // if(phoneNumber.matches("(0[3|5|7|8|9])+([0-9]{8})\\b")) return true;
        if(phoneNumber.matches("(0[0-9])+([0-9]{8})\\b")) return true;
        return false;
    }

    public static String checkValidateSubject(String subjectName, String subjectCode) {
        if(subjectName.isEmpty()) return "Please input subject name";
        if(subjectCode.isEmpty()) return "Please input subject code";

        if(subjectService.checkSubjectNameExist(subjectName)) return "Subject name already exist";
        if(subjectService.checkSubjectCodeExist(subjectCode)) return "Subject code already exist";

        return null;
    }

    public static String checkValidateUpdateSubject(String subjectName, String subjectCode, int subjectManagerId, Subject subject) {
        if(subjectName.isEmpty()) return "Please input subject name";
        if(subjectCode.isEmpty()) return "Please input subject code";
        if(subjectManagerId == 0) return "Please input subject manager";

        if(!subject.getSubjectName().equals(subjectName) && subjectService.checkSubjectNameExist(subjectName)) return "Subject name already exist";
        if(!subject.getSubjectCode().equals(subjectCode) && subjectService.checkSubjectCodeExist(subjectCode)) return "Subject code already exist";

        return null;
    }

    public static String checkValidateUser(String email, String phone) {
        if (email.isEmpty() && phone.isEmpty()) return "Please input email or phone number";
        if (!email.isEmpty() && !userService.checkEmailDomain(email)) return "Invalid email domain";

        if (!email.isEmpty() && !Validate.validEmail(email)) return "Invalid email";
        if (!phone.isEmpty() && !Validate.validPhoneNumber(phone)) return "Invalid phone number";

        if (!email.isEmpty() && userService.checkExistMail(email)) return "Email existed!";
        if (!phone.isEmpty() && userService.checkExistPhoneNumber(phone)) return "Phone number existed!";

        return null;
    }

    public static String checkValidateUpdateUser(String email, String phone, User user) {
        if (!email.isEmpty() && !userService.checkEmailDomain(email)) return "Invalid email domain";

        if (!email.isEmpty() && !Validate.validEmail(email)) return "Invalid email";
        if (!phone.isEmpty() && !Validate.validPhoneNumber(phone)) return "Invalid phone number";

        if (!email.equals(user.getEmail()) && !email.isEmpty() && userService.checkExistMail(email)) return "Email existed!";
        if (!phone.equals(user.getPhone()) && !phone.isEmpty() && userService.checkExistPhoneNumber(phone)) return "Phone number existed!";

        return null;
    }
}
