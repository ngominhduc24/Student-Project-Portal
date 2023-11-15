package swp.studentprojectportal.utils;

public class Validate {
    /*
     * Function to validate email
     * Email must contain @ and .
     */
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
        return false;
    }

    /*
     * Function to validate password
     * Password must contain at least 8 characters, including at least 1 uppercase letter, 1 lowercase letter, and 1 number
     */
    public static boolean validPassword(String password) {
        if(password == null) return false;
        if(
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[0-9].*") &&
                password.length() >= 8
        ) {
            return true;
        }
        return false;
    }

    public static  boolean validFullname(String fullname){
        if (fullname == null || fullname.isEmpty()) {
            return false;
        }


        // Kiểm tra xem chuỗi fullname có chứa số hay không
        if(fullname.matches(".*[0-9].*")) return false;

        // Kiểm tra xem chuỗi fullname có chứa ký tự đặc biệt hay không
        if(fullname.matches(".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*")) return false;

        return true;
    }

    public static  boolean validNotempty(String str){
        if (str == null || str.isEmpty() || str.trim().isEmpty()) {
            return false;
        }
        return  true;
    }

    public static  boolean validAvatarurl(String avatarurl){
        if (avatarurl == null || avatarurl.isEmpty() || avatarurl.trim().isEmpty()) {
            return false;
        }
        return  true;
    }

    public static  boolean validTilteDescription(String title, String description){

        if(title.matches(".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*")) return false;
        if(description.matches(".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*")) return false;

        return true;
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        return str.matches("\\d+"); // \\d+ kiểm tra xem chuỗi có toàn bộ là các chữ số hay không
    }
}
