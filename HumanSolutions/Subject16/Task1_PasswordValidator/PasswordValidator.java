public class PasswordValidator {
    public static boolean is_strong_password(final String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasDigit = false;
        boolean hasUppercase = false;
        boolean hasSpecialCharacter = false;
        final String specials = "!@#$%^&*()-_=+[]{}|;:',.<>/?`~";
        
        for (final char c : password.toCharArray()) {
            if (Character.isDigit(c)) hasDigit = true;
            else if (Character.isUpperCase(c)) hasUppercase = true;
            else if (specials.indexOf(c) != -1) hasSpecialCharacter = true;
        }
        return hasDigit && hasUppercase && hasSpecialCharacter;
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
