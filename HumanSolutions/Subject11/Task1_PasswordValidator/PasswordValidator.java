public class PasswordValidator {
    public static boolean is_strong_password(String password) {
        if (password == null || password.length() < 8) return false;
        boolean d = false, u = false, s = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (c >= '0' && c <= '9') d = true;
            else if (c >= 'A' && c <= 'Z') u = true;
            else if ("!@#$%^&*()-_=+[]{}|;:',.<>/?`~".indexOf(c) != -1) s = true;
        }
        return d && u && s;
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
