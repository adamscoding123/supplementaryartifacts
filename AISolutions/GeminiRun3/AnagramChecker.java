import java.util.Map;
import java.util.HashMap;
public class AnagramChecker {
    public boolean isAnagram(String str1, String str2) {
        if (str1 == null || str2 == null) return false;
        Map<Character, Integer> map = new HashMap<>();
        for (char c : str1.toLowerCase().toCharArray()) {
            if (!Character.isWhitespace(c)) map.put(c, map.getOrDefault(c, 0) + 1);
        }
        for (char c : str2.toLowerCase().toCharArray()) {
            if (!Character.isWhitespace(c)) map.put(c, map.getOrDefault(c, 0) - 1);
        }
        return map.values().stream().allMatch(v -> v == 0);
    }

    public static void main(String[] args) {
        AnagramChecker ac = new AnagramChecker();
        System.out.println("listen, silent -> " + ac.isAnagram("listen", "silent")); // true
        System.out.println("Listen, Silent -> " + ac.isAnagram("Listen", "Silent")); // true
        System.out.println("hello, world -> " + ac.isAnagram("hello", "world")); // false
        System.out.println("a b c, cba -> " + ac.isAnagram("a b c", "cba")); // true
        System.out.println("empty -> " + ac.isAnagram("", "")); // true
    }
}