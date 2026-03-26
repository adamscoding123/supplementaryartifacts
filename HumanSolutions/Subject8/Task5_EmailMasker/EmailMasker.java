public class EmailMasker {
    public static String mask_email(String e) {
        if (e == null || !e.contains("@") || e.trim().isEmpty()) return "Invalid Input";
        String[] p = e.split("@", 2);
        return (p[0].length() == 0) ? "Invalid Input" : p[0].charAt(0) + "***@" + p[1];
    }

    public static void main(String[] args) {
        System.out.println("adam.hamid@hiq.se -> " + mask_email("adam.hamid@hiq.se"));
        System.out.println("test@example.com -> " + mask_email("test@example.com"));
        System.out.println("invalidemail -> " + mask_email("invalidemail"));
    }
}
