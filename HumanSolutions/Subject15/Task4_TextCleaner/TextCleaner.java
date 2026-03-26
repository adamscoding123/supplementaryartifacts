public class TextCleaner {
    public static String clean_text(Object input) {
        if (input == null || !(input instanceof String)) return "";
        String s = (String) input;
        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) if (c < '0' || c > '9') sb.append(c);
        return sb.toString().trim();
    }
    public static void main(String[] args) {
        System.out.println("Hello123World -> \"" + clean_text("Hello123World") + "\"");
        System.out.println(" Hello  -> \"" + clean_text(" Hello ") + "\"");
        System.out.println("123 -> \"" + clean_text(123) + "\"");
    }
}
