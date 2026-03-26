public class EmailMasker {
    public static String mask_email(String email) {
        if (!isValid(email)) return "Invalid Input";
        int at = email.indexOf('@');
        return email.substring(0, 1) + "***" + email.substring(at);
    }
    private static boolean isValid(String s) {
        return s != null && !s.isEmpty() && s.contains("@") && s.indexOf('@') > 0;
    }
    public static void main(String[] args) {
        System.out.println("adam.hamid@hiq.se -> " + mask_email("adam.hamid@hiq.se"));
        System.out.println("test@example.com -> " + mask_email("test@example.com"));
        System.out.println("invalidemail -> " + mask_email("invalidemail"));
    }
}
