public class PasswordValidator {
    public static boolean is_strong_password(String password) {
        if (password == null || password.length() < 8) return false;
        
        return password.chars().anyMatch(Character::isDigit) &&
               password.chars().anyMatch(Character::isUpperCase) &&
               password.chars().anyMatch(ch -> "!@#$%^&*()-_=+[]{}|;:',.<>/?`~".indexOf(ch) >= 0);
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
