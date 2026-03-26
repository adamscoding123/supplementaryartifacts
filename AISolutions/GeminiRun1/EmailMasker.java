public class EmailMasker {
    public String maskEmail(String email) {
        if (email == null || email.isEmpty() || !email.contains("@")) return "Invalid Input";
        int atIndex = email.indexOf("@");
        if (atIndex == 0) return "Invalid Input";
        String domain = email.substring(atIndex);
        return email.charAt(0) + "***" + domain;
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