public class TextCleaner {
    public static String clean_text(Object data) {
        if (data == null || !(data instanceof String)) {
            return "";
        }
        
        String inputStr = (String) data;
        // Remove all digits and trim
        return inputStr.replaceAll("\\d", "").trim();
    }

    public static void main(String[] args) {
        System.out.println("Hello123World -> \"" + clean_text("Hello123World") + "\"");
        System.out.println(" Hello  -> \"" + clean_text(" Hello ") + "\"");
        System.out.println(" User 123 Name  -> \"" + clean_text(" User 123 Name ") + "\"");
        System.out.println("123 -> \"" + clean_text(123) + "\"");
        System.out.println("null -> \"" + clean_text(null) + "\"");
    }
}
