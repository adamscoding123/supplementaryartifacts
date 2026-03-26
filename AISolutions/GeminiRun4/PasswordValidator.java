import java.util.regex.Pattern;
public class PasswordValidator {
    private static final Pattern DIGIT = Pattern.compile("[0-9]");
    private static final Pattern UPPER = Pattern.compile("[A-Z]");
    private static final Pattern SPECIAL = Pattern.compile("[^a-zA-Z0-9\\s]");
    public boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) return false;
        return DIGIT.matcher(password).find() && UPPER.matcher(password).find() && SPECIAL.matcher(password).find();
    }

    public static void main(String[] args) {
        PasswordValidator pv = new PasswordValidator();
        System.out.println("P@ssword1 -> " + pv.isStrongPassword("P@ssword1")); // Expected: true
        System.out.println("12345678 -> " + pv.isStrongPassword("12345678")); // Expected: false
        System.out.println("Short1! -> " + pv.isStrongPassword("Short1!")); // Expected: false
        System.out.println("noupper1! -> " + pv.isStrongPassword("noupper1!")); // Expected: false
        System.out.println("NO_LOWER_1 -> " + pv.isStrongPassword("NO_LOWER_1")); // Expected: true
    }
}