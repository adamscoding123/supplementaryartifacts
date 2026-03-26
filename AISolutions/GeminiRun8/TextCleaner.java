public class TextCleaner {
    public String cleanText(String input) {
        if (input == null) return "";
        char[] chars = input.toCharArray();
        StringBuilder cleaned = new StringBuilder();
        for (char c : chars) {
            if (c < '0' || c > '9') cleaned.append(c);
        }
        return cleaned.toString().strip();
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
