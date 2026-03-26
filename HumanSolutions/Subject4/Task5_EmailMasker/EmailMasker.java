public class EmailMasker {
    public static String mask_email(String email) {
        if (email == null || email.isEmpty() || !email.contains("@")) {
            return "Invalid Input";
        }
        
        int atIndex = email.indexOf("@");
        if (atIndex <= 0) { // No username part or starts with @
            return "Invalid Input";
        }
        
        String firstChar = email.substring(0, 1);
        String domainPart = email.substring(atIndex);
        
        return firstChar + "***" + domainPart;
    }

    public static void main(String[] args) {
        System.out.println("adam.hamid@hiq.se -> " + mask_email("adam.hamid@hiq.se")); // a***@hiq.se
        System.out.println("test@example.com -> " + mask_email("test@example.com")); // t***@example.com
        System.out.println("invalidemail -> " + mask_email("invalidemail")); // Invalid Input
        System.out.println("empty -> " + mask_email("")); // Invalid Input
    }
}
