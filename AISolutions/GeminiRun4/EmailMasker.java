import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class EmailMasker {
    private static final Pattern PATTERN = Pattern.compile("^([^@])([^@]*)@(.+)$");
    public String maskEmail(String email) {
        if (email == null || email.isEmpty()) return "Invalid Input";
        Matcher m = PATTERN.matcher(email);
        if (m.matches()) {
            return m.group(1) + "***@" + m.group(3);
        }
        return "Invalid Input";
    }

    public static void main(String[] args) {
        EmailMasker em = new EmailMasker();
        System.out.println("user.name@email.com -> " + em.maskEmail("user.name@email.com"));
        System.out.println("hello@test.com -> " + em.maskEmail("hello@test.com"));
        System.out.println("a@b.com -> " + em.maskEmail("a@b.com"));
        System.out.println("invalid -> " + em.maskEmail("invalidemail"));
        System.out.println("empty -> " + em.maskEmail(""));
    }
}