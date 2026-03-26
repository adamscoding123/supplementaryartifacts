public class EmailMasker {
    public String maskEmail(String email) {
        if (email == null || !email.contains("@")) return "Invalid Input";
        int atIdx = email.lastIndexOf("@");
        if (atIdx == 0) return "Invalid Input";
        String local = email.substring(0, atIdx);
        String domain = email.substring(atIdx);
        return local.charAt(0) + "***" + domain;
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
