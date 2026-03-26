public class EmailMasker {
    public static String mask_email(String email) {
        if (email == null || email.indexOf('@') == -1 || email.isEmpty()) {
            return "Invalid Input";
        }
        
        int atIndex = email.indexOf('@');
        if (atIndex == 0) return "Invalid Input";
        
        String first = email.substring(0, 1);
        String domain = email.substring(atIndex);
        
        return first + "***" + domain;
    }

    public static void main(String[] args) {
        System.out.println("adam.hamid@hiq.se -> " + mask_email("adam.hamid@hiq.se"));
        System.out.println("test@example.com -> " + mask_email("test@example.com"));
        System.out.println("invalidemail -> " + mask_email("invalidemail"));
    }
}
