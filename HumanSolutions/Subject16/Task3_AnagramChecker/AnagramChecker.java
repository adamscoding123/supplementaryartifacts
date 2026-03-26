import java.util.Arrays;

public class AnagramChecker {
    public static boolean is_anagram(final String str1, final String str2) {
        if (str1 == null || str2 == null) {
            return false;
        }
        final String s1 = str1.toLowerCase().replace(" ", "");
        final String s2 = str2.toLowerCase().replace(" ", "");
        if (s1.length() != s2.length() || s1.isEmpty()) {
            return false;
        }
        final char[] charArray1 = s1.toCharArray();
        final char[] charArray2 = s2.toCharArray();
        Arrays.sort(charArray1);
        Arrays.sort(charArray2);
        return Arrays.equals(charArray1, charArray2);
    }
    public static void main(String[] args) {
        System.out.println("listen, silent -> " + is_anagram("listen", "silent"));
        System.out.println("Debit card, Bad credit -> " + is_anagram("Debit card", "Bad credit"));
        System.out.println("hello, world -> " + is_anagram("hello", "world"));
    }
}
