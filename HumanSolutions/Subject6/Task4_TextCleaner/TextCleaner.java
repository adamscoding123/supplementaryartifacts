import java.util.stream.Collectors;

public class TextCleaner {
    public static String clean_text(Object inputData) {
        if (!(inputData instanceof String)) {
            return "";
        }
        
        String inputStr = (String) inputData;
        return inputStr.chars()
                .filter(c -> !Character.isDigit(c))
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining())
                .trim();
    }

    public static void main(String[] args) {
        System.out.println("Hello123World -> \"" + clean_text("Hello123World") + "\"");
        System.out.println(" Hello  -> \"" + clean_text(" Hello ") + "\"");
        System.out.println(" User 123 Name  -> \"" + clean_text(" User 123 Name ") + "\"");
        System.out.println("123 -> \"" + clean_text(123) + "\"");
        System.out.println("null -> \"" + clean_text(null) + "\"");
    }
}
