public class PasswordValidator {
    public static boolean is_strong_password(String password) {
        if (password == null || password.length() < 8) return false;
        
        boolean[] criteria = new boolean[3]; // [digit, upper, special]
        String specials = "!@#$%^&*()-_=+[]{}|;:',.<>/?`~";
        
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) criteria[0] = true;
            else if (Character.isUpperCase(c)) criteria[1] = true;
            else if (specials.indexOf(c) != -1) criteria[2] = true;
        }
        
        return criteria[0] && criteria[1] && criteria[2];
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
