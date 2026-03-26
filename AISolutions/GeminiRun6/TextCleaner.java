public class TextCleaner {
    public String cleanText(String input) {
        if (input == null) return "";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (!Character.isDigit(ch)) result.append(ch);
        }
        return result.toString().trim();
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
