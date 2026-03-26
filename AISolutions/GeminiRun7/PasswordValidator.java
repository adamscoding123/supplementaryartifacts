public class PasswordValidator {
    public boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) return false;
        boolean digitFound = password.chars().anyMatch(Character::isDigit);
        boolean upperFound = password.chars().anyMatch(Character::isUpperCase);
        boolean specialFound = password.chars().anyMatch(c -> !Character.isLetterOrDigit(c) && !Character.isWhitespace(c));
        return digitFound && upperFound && specialFound;
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
