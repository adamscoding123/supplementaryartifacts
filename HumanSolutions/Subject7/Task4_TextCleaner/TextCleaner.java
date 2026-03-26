public class TextCleaner {
    public static String clean_text(Object input) {
        if (!(input instanceof String)) return "";
        
        String s = (String) input;
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch < '0' || ch > '9') {
                result.append(ch);
            }
        }
        return result.toString().trim();
    }

    public static void main(String[] args) {
        System.out.println("Hello123World -> \"" + clean_text("Hello123World") + "\"");
        System.out.println(" Hello  -> \"" + clean_text(" Hello ") + "\"");
        System.out.println("123 -> \"" + clean_text(123) + "\"");
    }
}
