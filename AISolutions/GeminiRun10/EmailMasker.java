public class EmailMasker {
    public String maskEmail(String email) {
        if (email == null || email.isEmpty()) return "Invalid Input";
        String[] halves = email.split("@", 2);
        if (halves.length != 2 || halves[0].isEmpty()) return "Invalid Input";
        return halves[0].charAt(0) + "***@" + halves[1];
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
