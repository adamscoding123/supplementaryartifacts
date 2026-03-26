public class TextCleaner {
    public static String clean_text(Object input) {
        if (!(input instanceof String)) return "";
        String s = (String) input;
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') res += c;
        }
        return res.trim();
    }
    public static void main(String[] args) {
        System.out.println("Hello123World -> \"" + clean_text("Hello123World") + "\"");
        System.out.println(" Hello  -> \"" + clean_text(" Hello ") + "\"");
        System.out.println("123 -> \"" + clean_text(123) + "\"");
    }
}
