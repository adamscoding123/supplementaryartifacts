import java.util.Map;
import java.util.HashMap;
public class AnagramChecker {
    public boolean isAnagram(String str1, String str2) {
        if (str1 == null || str2 == null) return false;
        String clean1 = str1.toLowerCase().replaceAll("\\s+", "");
        String clean2 = str2.toLowerCase().replaceAll("\\s+", "");
        if (clean1.length() != clean2.length()) return false;
        Map<Character, Integer> charCounts = new HashMap<>();
        for (char c : clean1.toCharArray()) {
            charCounts.merge(c, 1, Integer::sum);
        }
        for (char c : clean2.toCharArray()) {
            charCounts.merge(c, -1, Integer::sum);
        }
        return charCounts.values().stream().allMatch(count -> count == 0);
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
