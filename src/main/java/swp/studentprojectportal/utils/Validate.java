package swp.studentprojectportal.utils;

public class Validate {
    public static boolean validEmail(String email) {
        if(email == null) return false;
        if(email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) return true;
        return false;
    }

    public static boolean validPhoneNumber(String phoneNumber) {
        if(phoneNumber == null) return false;
        phoneNumber = phoneNumber.replace("+84", "0");
        if(phoneNumber.matches("(0[3|5|7|8|9])+([0-9]{8})\\b")) return true;
        return false;
    }
}
