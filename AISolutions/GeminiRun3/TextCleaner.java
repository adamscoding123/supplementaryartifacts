import java.util.stream.Collectors;
public class TextCleaner {
    public String cleanText(String input) {
        if (input == null) return "";
        return input.chars()
                .filter(c -> !Character.isDigit(c))
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining())
                .trim();
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