public class EmailMasker {
    public static String mask_email(final String email) {
        if (email == null || email.trim().isEmpty() || !email.contains("@") || email.startsWith("@")) {
            return "Invalid Input";
        }
        final int atSymbolPosition = email.indexOf('@');
        final String domainPart = email.substring(atSymbolPosition);
        final char firstCharacter = email.charAt(0);
        return firstCharacter + "***" + domainPart;
    }
    public static void main(String[] args) {
        System.out.println("adam.hamid@hiq.se -> " + mask_email("adam.hamid@hiq.se"));
        System.out.println("test@example.com -> " + mask_email("test@example.com"));
        System.out.println("invalidemail -> " + mask_email("invalidemail"));
    }
}
