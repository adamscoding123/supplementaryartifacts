public class TextCleaner {
    public String cleanText(String input) {
        if (input == null) return "";
        String withoutDigits = "";
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                withoutDigits += input.charAt(i);
            }
        }
        return withoutDigits.trim();
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
