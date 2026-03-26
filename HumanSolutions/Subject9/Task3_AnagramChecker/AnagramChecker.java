import java.util.Arrays;

public class AnagramChecker {
    public static boolean is_anagram(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        
        return getSortedChars(s1).equals(getSortedChars(s2));
    }
    
    private static String getSortedChars(String s) {
        char[] chars = s.toLowerCase().replaceAll("\\s", "").toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public static void main(String[] args) {
        System.out.println("listen, silent -> " + is_anagram("listen", "silent"));
        System.out.println("Debit card, Bad credit -> " + is_anagram("Debit card", "Bad credit"));
        System.out.println("hello, world -> " + is_anagram("hello", "world"));
    }
}
