import java.util.stream.Collectors;

public class TextCleaner {
    public static String clean_text(Object input) {
        if (!(input instanceof String)) return "";
        return ((String)input).chars().filter(c -> !Character.isDigit(c))
                               .mapToObj(c -> String.valueOf((char)c))
                               .collect(Collectors.joining()).trim();
    }
    public static void main(String[] args) {
        System.out.println("Hello123World -> \"" + clean_text("Hello123World") + "\"");
        System.out.println(" Hello  -> \"" + clean_text(" Hello ") + "\"");
        System.out.println("123 -> \"" + clean_text(123) + "\"");
    }
}
