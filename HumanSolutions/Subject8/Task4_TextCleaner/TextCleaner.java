public class TextCleaner {
    public static String clean_text(Object o) {
        return (o instanceof String) ? ((String)o).replaceAll("\\d", "").trim() : "";
    }

    public static void main(String[] args) {
        System.out.println("Hello123World -> \"" + clean_text("Hello123World") + "\"");
        System.out.println(" Hello  -> \"" + clean_text(" Hello ") + "\"");
        System.out.println("123 -> \"" + clean_text(123) + "\"");
    }
}
