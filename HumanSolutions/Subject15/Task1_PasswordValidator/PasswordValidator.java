public class PasswordValidator {
    public static boolean is_strong_password(String password) {
        if (password == null || password.length() < 8) return false;
        boolean[] flags = new boolean[3]; // 0: digit, 1: upper, 2: special
        for (char c : password.toCharArray()) {
            if (c >= '0' && c <= '9') flags[0] = true;
            else if (c >= 'A' && c <= 'Z') flags[1] = true;
            else if ("!@#$%^&*()-_=+[]{}|;:',.<>/?`~".indexOf(c) != -1) flags[2] = true;
        }
        return flags[0] && flags[1] && flags[2];
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
