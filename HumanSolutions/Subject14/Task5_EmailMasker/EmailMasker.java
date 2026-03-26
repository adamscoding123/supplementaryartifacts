public class EmailMasker {
    public static String mask_email(String email) {
        if (email == null || !email.matches(".+@.+")) return "Invalid Input";
        return email.replaceAll("^(.).*(@.*)$", "$1***$2");
    }
    public static void main(String[] args) {
        System.out.println("adam.hamid@hiq.se -> " + mask_email("adam.hamid@hiq.se"));
        System.out.println("test@example.com -> " + mask_email("test@example.com"));
        System.out.println("invalidemail -> " + mask_email("invalidemail"));
    }
}
