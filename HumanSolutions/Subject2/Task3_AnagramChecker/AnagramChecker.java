import java.util.Map;
import java.util.HashMap;

public class AnagramChecker {
    public static boolean is_anagram(String str1, String str2) {
        if (str1 == null || str2 == null) return false;
        String s1 = str1.replaceAll("\\s", "").toLowerCase();
        String s2 = str2.replaceAll("\\s", "").toLowerCase();
        if (s1.length() != s2.length()) return false;
        
        Map<Character, Integer> counts = new HashMap<>();
        for (char c : s1.toCharArray()) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }
        for (char c : s2.toCharArray()) {
            if (!counts.containsKey(c)) return false;
            counts.put(c, counts.get(c) - 1);
            if (counts.get(c) == 0) counts.remove(c);
        }
        return counts.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println("listen, silent -> " + is_anagram("listen", "silent")); // true
        System.out.println("Debit card, Bad credit -> " + is_anagram("Debit card", "Bad credit")); // true
        System.out.println("hello, world -> " + is_anagram("hello", "world")); // false
    }
}