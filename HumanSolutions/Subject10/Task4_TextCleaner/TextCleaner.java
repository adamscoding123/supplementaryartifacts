public class TextCleaner {
    public static String clean_text(Object inputObj) {
        if (inputObj == null || !(inputObj instanceof String)) return "";
        
        String input = (String) inputObj;
        return input.replaceAll("[0-9]", "").trim();
    }

    public static void main(String[] args) {
        System.out.println("Hello123World -> \"" + clean_text("Hello123World") + "\"");
        System.out.println(" Hello  -> \"" + clean_text(" Hello ") + "\"");
        System.out.println("123 -> \"" + clean_text(123) + "\"");
    }
}
