public class EmailMasker {
    public static String mask_email(String email) {
        if (email == null || email.isEmpty() || !email.contains("@")) return "Invalid Input";
        
        String[] parts = email.split("@");
        if (parts.length < 2 || parts[0].isEmpty()) return "Invalid Input";
        
        StringBuilder sb = new StringBuilder();
        sb.append(parts[0].charAt(0)).append("***@").append(parts[1]);
        
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("adam.hamid@hiq.se -> " + mask_email("adam.hamid@hiq.se"));
        System.out.println("test@example.com -> " + mask_email("test@example.com"));
        System.out.println("invalidemail -> " + mask_email("invalidemail"));
    }
}
