import java.util.Arrays;

public class AnagramChecker {
    public static boolean is_anagram(String stringA, String stringB) {
        if (stringA == null || stringB == null) return false;
        
        char[] charsA = stringA.toLowerCase().replaceAll("\\s", "").toCharArray();
        char[] charsB = stringB.toLowerCase().replaceAll("\\s", "").toCharArray();
        
        if (charsA.length != charsB.length) return false;
        
        Arrays.sort(charsA);
        Arrays.sort(charsB);
        
        return Arrays.equals(charsA, charsB);
    }

    public static void main(String[] args) {
        System.out.println("listen, silent -> " + is_anagram("listen", "silent")); // true
        System.out.println("Debit card, Bad credit -> " + is_anagram("Debit card", "Bad credit")); // true
        System.out.println("hello, world -> " + is_anagram("hello", "world")); // false
    }
}
