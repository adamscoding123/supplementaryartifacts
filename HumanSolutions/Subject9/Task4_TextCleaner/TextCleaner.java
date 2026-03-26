public class TextCleaner {
    public static String clean_text(Object input) {
        if (input instanceof String) {
            String s = (String) input;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (!Character.isDigit(c)) sb.append(c);
            }
            return sb.toString().trim();
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println("Hello123World -> \"" + clean_text("Hello123World") + "\"");
        System.out.println(" Hello  -> \"" + clean_text(" Hello ") + "\"");
        System.out.println("123 -> \"" + clean_text(123) + "\"");
    }
}
