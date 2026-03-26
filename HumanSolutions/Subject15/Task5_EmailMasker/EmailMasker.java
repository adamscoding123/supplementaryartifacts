public class EmailMasker {
    public static String mask_email(String email) {
        if (email == null || email.isEmpty() || email.indexOf('@') == -1 || email.indexOf('@') == 0) return "Invalid Input";
        String[] parts = email.split("@", 2);
        return parts[0].substring(0, 1) + "***@" + parts[1];
    }
    public static void main(String[] args) {
        System.out.println("adam.hamid@hiq.se -> " + mask_email("adam.hamid@hiq.se"));
        System.out.println("test@example.com -> " + mask_email("test@example.com"));
        System.out.println("invalidemail -> " + mask_email("invalidemail"));
    }
}
