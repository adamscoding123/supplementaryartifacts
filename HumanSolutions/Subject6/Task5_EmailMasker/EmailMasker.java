public class EmailMasker {
    public static String mask_email(String emailStr) {
        if (emailStr == null || emailStr.trim().isEmpty() || !emailStr.contains("@")) {
            return "Invalid Input";
        }
        
        int atPos = emailStr.indexOf('@');
        if (atPos == 0) return "Invalid Input";
        
        StringBuilder masked = new StringBuilder();
        masked.append(emailStr.charAt(0));
        masked.append("***");
        masked.append(emailStr.substring(atPos));
        
        return masked.toString();
    }

    public static void main(String[] args) {
        System.out.println("adam.hamid@hiq.se -> " + mask_email("adam.hamid@hiq.se")); // a***@hiq.se
        System.out.println("test@example.com -> " + mask_email("test@example.com")); // t***@example.com
        System.out.println("invalidemail -> " + mask_email("invalidemail")); // Invalid Input
        System.out.println("empty -> " + mask_email("")); // Invalid Input
    }
}
