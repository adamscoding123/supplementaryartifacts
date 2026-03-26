import java.util.Arrays;

public class AnagramChecker {
    public static boolean is_anagram(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        
        String clean1 = s1.replaceAll("\\s", "").toLowerCase();
        String clean2 = s2.replaceAll("\\s", "").toLowerCase();
        
        if (clean1.length() != clean2.length()) return false;
        
        int[] counts = new int[256]; // Assuming standard ASCII/Unicode subset for simplicity
        for (char c : clean1.toCharArray()) {
            counts[c]++;
        }
        for (char c : clean2.toCharArray()) {
            counts[c]--;
            if (counts[c] < 0) return false;
        }
        
        return true;
    }

    public static void main(String[] args) {
        System.out.println("listen, silent -> " + is_anagram("listen", "silent")); // true
        System.out.println("Debit card, Bad credit -> " + is_anagram("Debit card", "Bad credit")); // true
        System.out.println("hello, world -> " + is_anagram("hello", "world")); // false
    }
}
