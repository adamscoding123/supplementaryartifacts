import java.util.HashMap;
import java.util.Map;

public class AnagramChecker {
    public static boolean is_anagram(String input1, String input2) {
        if (input1 == null || input2 == null) return false;
        
        String s1 = input1.replace(" ", "").toLowerCase();
        String s2 = input2.replace(" ", "").toLowerCase();
        
        if (s1.length() != s2.length()) return false;
        
        Map<Character, Integer> charMap = new HashMap<>();
        for (char c : s1.toCharArray()) {
            charMap.put(c, charMap.getOrDefault(c, 0) + 1);
        }
        
        for (char c : s2.toCharArray()) {
            if (!charMap.containsKey(c)) return false;
            charMap.put(c, charMap.get(c) - 1);
            if (charMap.get(c) < 0) return false;
        }
        
        return true;
    }

    public static void main(String[] args) {
        System.out.println("listen, silent -> " + is_anagram("listen", "silent")); // true
        System.out.println("Debit card, Bad credit -> " + is_anagram("Debit card", "Bad credit")); // true
        System.out.println("hello, world -> " + is_anagram("hello", "world")); // false
    }
}
