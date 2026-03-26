public class EmailMasker {
    public String maskEmail(String email) {
        if (email == null || email.isEmpty()) return "Invalid Input";
        int atPos = email.indexOf("@");
        if (atPos <= 0) return "Invalid Input";
        String firstChar = email.substring(0, 1);
        String domainPart = email.substring(atPos);
        return firstChar + "***" + domainPart;
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
