public class EmailMasker {
    public static String mask_email(String email) {
        if (email == null || email.indexOf('@') == -1 || email.trim().isEmpty()) {
            return "Invalid Input";
        }
        
        String[] parts = email.split("@");
        if (parts.length < 2 || parts[0].isEmpty()) {
            return "Invalid Input";
        }
        
        String username = parts[0];
        String domain = email.substring(email.indexOf('@')); // Use original index for domain
        
        char firstChar = username.charAt(0);
        return firstChar + "***" + domain;
    }

    public static void main(String[] args) {
        System.out.println("adam.hamid@hiq.se -> " + mask_email("adam.hamid@hiq.se")); // a***@hiq.se
        System.out.println("test@example.com -> " + mask_email("test@example.com")); // t***@example.com
        System.out.println("invalidemail -> " + mask_email("invalidemail")); // Invalid Input
        System.out.println("empty -> " + mask_email("")); // Invalid Input
    }
}
