public class PasswordValidator {
    public static boolean is_strong_password(String p) {
        if (p == null || p.length() < 8) return false;
        return hasDigit(p) && hasUpper(p) && hasSpecial(p);
    }
    private static boolean hasDigit(String s) {
        for (char c : s.toCharArray()) if (Character.isDigit(c)) return true;
        return false;
    }
    private static boolean hasUpper(String s) {
        for (char c : s.toCharArray()) if (Character.isUpperCase(c)) return true;
        return false;
    }
    private static boolean hasSpecial(String s) {
        String spec = "!@#$%^&*()-_=+[]{}|;:',.<>/?`~";
        for (char c : s.toCharArray()) if (spec.indexOf(c) != -1) return true;
        return false;
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
