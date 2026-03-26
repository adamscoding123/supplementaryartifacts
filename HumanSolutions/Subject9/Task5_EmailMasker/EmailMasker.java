public class EmailMasker {
    public static String mask_email(String email) {
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            return "Invalid Input";
        }
        
        int atIndex = email.indexOf('@');
        if (atIndex == 0) return "Invalid Input";
        
        return String.format("%c***%s", email.charAt(0), email.substring(atIndex));
    }

    public static void main(String[] args) {
        System.out.println("adam.hamid@hiq.se -> " + mask_email("adam.hamid@hiq.se"));
        System.out.println("test@example.com -> " + mask_email("test@example.com"));
        System.out.println("invalidemail -> " + mask_email("invalidemail"));
    }
}
