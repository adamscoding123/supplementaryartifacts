public class TextCleaner {
    public static String clean_text(Object inputStr) {
        if (!(inputStr instanceof String)) return "";
        String str = (String) inputStr;
        StringBuilder sb = new StringBuilder();
        boolean spacePending = false;
        boolean hasChars = false;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) continue;
            
            if (Character.isWhitespace(c)) {
                if (hasChars) spacePending = true;
            } else {
                if (spacePending && hasChars) {
                    sb.append(" ");
                }
                sb.append(c);
                hasChars = true;
                spacePending = false;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("Hello123World -> \"" + clean_text("Hello123World") + "\"");
        System.out.println(" Hello  -> \"" + clean_text(" Hello ") + "\"");
        System.out.println(" User 123 Name  -> \"" + clean_text(" User 123 Name ") + "\"");
        System.out.println("123 -> \"" + clean_text(123) + "\"");
        System.out.println("null -> \"" + clean_text(null) + "\"");
    }
}