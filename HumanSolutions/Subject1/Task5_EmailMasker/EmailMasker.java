public class EmailMasker {
    public static String mask_email(String email) {
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            return "Invalid Input";
        }
        String[] parts = email.split("@", 2);
        if (parts.length < 2) {
            return "Invalid Input";
        }
        String username = parts[0];
        String domain = parts[1];
        if (username.isEmpty()) {
            return "Invalid Input";
        }
        String maskedUsername = username.charAt(0) + "***";
        return maskedUsername + "@" + domain;
    }

    public static void main(String[] args) {
        System.out.println("adam.hamid@hiq.se -> " + mask_email("adam.hamid@hiq.se")); // a***@hiq.se
        System.out.println("test@example.com -> " + mask_email("test@example.com")); // t***@example.com
        System.out.println("invalidemail -> " + mask_email("invalidemail")); // Invalid Input
        System.out.println("empty -> " + mask_email("")); // Invalid Input
    }
}
