public class PasswordValidator {
    public static boolean is_strong_password(String pwd) {
        if (pwd == null || pwd.length() < 8) return false;
        
        boolean digitFound = false;
        boolean upperFound = false;
        boolean specialFound = false;
        
        for (int i = 0; i < pwd.length(); i++) {
            char c = pwd.charAt(i);
            if (Character.isDigit(c)) digitFound = true;
            else if (Character.isUpperCase(c)) upperFound = true;
            else if (!Character.isLetterOrDigit(c)) specialFound = true;
        }
        
        return digitFound && upperFound && specialFound;
    }

    public static void main(String[] args) {
        System.out.println("Secure1! -> " + is_strong_password("Secure1!")); // true
        System.out.println("password -> " + is_strong_password("password")); // false
        System.out.println("Short1! -> " + is_strong_password("Short1!")); // false
        System.out.println("NoDigit! -> " + is_strong_password("NoDigit!")); // false
        System.out.println("noupper1! -> " + is_strong_password("noupper1!")); // false
        System.out.println("NoSpecial1 -> " + is_strong_password("NoSpecial1")); // false
    }
}
