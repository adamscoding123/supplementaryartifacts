public class TextCleaner {
    public static String clean_text(final Object input) {
        if (input == null || !(input instanceof String)) {
            return "";
        }
        final String text = (String) input;
        final StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            final char currentChar = text.charAt(i);
            if (!Character.isDigit(currentChar)) {
                resultBuilder.append(currentChar);
            }
        }
        return resultBuilder.toString().trim();
    }
    public static void main(String[] args) {
        System.out.println("Hello123World -> \"" + clean_text("Hello123World") + "\"");
        System.out.println(" Hello  -> \"" + clean_text(" Hello ") + "\"");
        System.out.println("123 -> \"" + clean_text(123) + "\"");
    }
}
