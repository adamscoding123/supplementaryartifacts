import java.util.Optional;

public class EmailMasker {
    public static String mask_email(String email) {
        return Optional.ofNullable(email)
                .filter(e -> !e.trim().isEmpty())
                .filter(e -> e.contains("@"))
                .filter(e -> e.indexOf("@") > 0)
                .map(e -> e.charAt(0) + "***" + e.substring(e.indexOf("@")))
                .orElse("Invalid Input");
    }
    public static void main(String[] args) {
        System.out.println("adam.hamid@hiq.se -> " + mask_email("adam.hamid@hiq.se"));
        System.out.println("test@example.com -> " + mask_email("test@example.com"));
        System.out.println("invalidemail -> " + mask_email("invalidemail"));
    }
}
