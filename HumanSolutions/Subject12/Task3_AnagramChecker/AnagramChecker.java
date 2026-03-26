import java.util.Arrays;
import java.util.stream.Collectors;

public class AnagramChecker {
    public static boolean is_anagram(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        String a = s1.chars().filter(c -> !Character.isWhitespace(c)).map(Character::toLowerCase).sorted()
                     .mapToObj(c -> String.valueOf((char)c)).collect(Collectors.joining());
        String b = s2.chars().filter(c -> !Character.isWhitespace(c)).map(Character::toLowerCase).sorted()
                     .mapToObj(c -> String.valueOf((char)c)).collect(Collectors.joining());
        return !a.isEmpty() && a.equals(b);
    }
    public static void main(String[] args) {
        System.out.println("listen, silent -> " + is_anagram("listen", "silent"));
        System.out.println("Debit card, Bad credit -> " + is_anagram("Debit card", "Bad credit"));
        System.out.println("hello, world -> " + is_anagram("hello", "world"));
    }
}
