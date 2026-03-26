public class EmailMasker {
    public String maskEmail(String email) {
        if (email == null || !email.contains("@")) return "Invalid Input";
        String[] parts = email.split("@");
        if (parts.length != 2 || parts[0].isEmpty()) return "Invalid Input";
        return parts[0].substring(0, 1) + "***@" + parts[1];
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