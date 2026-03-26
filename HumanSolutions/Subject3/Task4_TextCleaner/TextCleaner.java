import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TextCleaner {
    public static String clean_text(Object inputStr) {
        if (!(inputStr instanceof String)) return "";
        String s = (String) inputStr;
        Matcher m = Pattern.compile("\\d").matcher(s);
        String noDigits = m.replaceAll("");
        return noDigits.replaceAll("^\\s+|\\s+$", "").replaceAll("\\s+", " ");
    }

    public static void main(String[] args) {
        System.out.println("Hello123World -> \"" + clean_text("Hello123World") + "\"");
        System.out.println(" Hello  -> \"" + clean_text(" Hello ") + "\"");
        System.out.println(" User 123 Name  -> \"" + clean_text(" User 123 Name ") + "\"");
        System.out.println("123 -> \"" + clean_text(123) + "\"");
        System.out.println("null -> \"" + clean_text(null) + "\"");
    }
}