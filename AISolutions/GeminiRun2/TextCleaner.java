public class TextCleaner {
    public String cleanText(String input) {
        if (input == null) return "";
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) sb.append(c);
        }
        return sb.toString().trim();
    }

    public static void main(String[] args) {
        TextCleaner tc = new TextCleaner();
        System.out.println("Hello123World -> " + tc.cleanText("Hello123World")); // HelloWorld
        System.out.println("' Hello ' -> " + tc.cleanText(" Hello ")); // Hello
        System.out.println("' User 123 Name ' -> " + tc.cleanText(" User 123 Name ")); // User Name
        System.out.println("null -> " + tc.cleanText(null)); // ""
        System.out.println("12345 -> " + tc.cleanText("12345")); // ""
    }
}