import java.util.regex.Pattern;
public class EmailMasker {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)$");
    public String maskEmail(String email) {
        if (email == null || email.isEmpty()) return "Invalid Input";
        var matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) return "Invalid Input";
        String localPart = matcher.group(1);
        String domainPart = matcher.group(2);
        if (localPart.isEmpty()) return "Invalid Input";
        return localPart.charAt(0) + "***@" + domainPart;
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
