import java.util.regex.Pattern;

public class PasswordValidator {
    public static boolean is_strong_password(String p) {
        if (p == null || p.length() < 8) return false;
        return Pattern.compile("(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+\\[\\]{}|;:',.<>/?`~])").matcher(p).find();
    }

    public static void main(String[] args) {
        System.out.println("Secure1! -> " + is_strong_password("Secure1!"));
        System.out.println("password -> " + is_strong_password("password"));
        System.out.println("Short1! -> " + is_strong_password("Short1!"));
        System.out.println("NoDigit! -> " + is_strong_password("NoDigit!"));
        System.out.println("noupper1! -> " + is_strong_password("noupper1!"));
        System.out.println("NoSpecial1 -> " + is_strong_password("NoSpecial1"));
    }
}
