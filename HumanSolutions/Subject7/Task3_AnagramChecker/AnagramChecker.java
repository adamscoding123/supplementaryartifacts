import java.util.Arrays;

public class AnagramChecker {
    public static boolean is_anagram(String word1, String word2) {
        if (word1 == null || word2 == null) return false;
        
        String s1 = word1.toLowerCase().replace(" ", "");
        String s2 = word2.toLowerCase().replace(" ", "");
        
        if (s1.length() != s2.length()) return false;
        
        char[] a1 = s1.toCharArray();
        char[] a2 = s2.toCharArray();
        
        Arrays.sort(a1);
        Arrays.sort(a2);
        
        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("listen, silent -> " + is_anagram("listen", "silent"));
        System.out.println("Debit card, Bad credit -> " + is_anagram("Debit card", "Bad credit"));
        System.out.println("hello, world -> " + is_anagram("hello", "world"));
    }
}
