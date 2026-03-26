public class TextCleaner {
    public static String clean_text(Object inputStr) {
        if (inputStr == null || !(inputStr instanceof String)) {
            return "";
        }
        String str = (String) inputStr;
        return str.replaceAll("\\d", "").replaceAll("\\s+", " ").trim();
    }

    public static void main(String[] args) {
        System.out.println("Hello123World -> \"" + clean_text("Hello123World") + "\"");
        System.out.println(" Hello  -> \"" + clean_text(" Hello ") + "\"");
        System.out.println(" User 123 Name  -> \"" + clean_text(" User 123 Name ") + "\"");
        System.out.println("123 -> \"" + clean_text(123) + "\"");
        System.out.println("null -> \"" + clean_text(null) + "\"");
    }
}
