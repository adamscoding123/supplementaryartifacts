public class PasswordValidator {
    public boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) return false;
        boolean digit = false, upper = false, special = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isDigit(c)) digit = true;
            else if (Character.isUpperCase(c)) upper = true;
            else if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) special = true;
            if (digit && upper && special) return true;
        }
        return digit && upper && special;
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
