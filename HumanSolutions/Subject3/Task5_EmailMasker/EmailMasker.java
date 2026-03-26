import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class EmailMasker {
    public static String mask_email(String email) {
        if (email == null) return "Invalid Input";
        Matcher matcher = Pattern.compile("^([^@])(?:[^@]*)@(.+)$").matcher(email.trim());
        if (matcher.matches()) {
            return matcher.group(1) + "***@" + matcher.group(2);
        }
        return "Invalid Input";
    }

    public static void main(String[] args) {
        System.out.println("adam.hamid@hiq.se -> " + mask_email("adam.hamid@hiq.se")); // a***@hiq.se
        System.out.println("test@example.com -> " + mask_email("test@example.com")); // t***@example.com
        System.out.println("invalidemail -> " + mask_email("invalidemail")); // Invalid Input
        System.out.println("empty -> " + mask_email("")); // Invalid Input
    }
}