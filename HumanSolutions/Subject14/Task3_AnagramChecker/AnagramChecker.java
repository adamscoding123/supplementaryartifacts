import java.util.Arrays;

public class AnagramChecker {
    public static boolean is_anagram(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        char[] a = s1.toLowerCase().replaceAll("\\s+", "").toCharArray();
        char[] b = s2.toLowerCase().replaceAll("\\s+", "").toCharArray();
        if (a.length != b.length || a.length == 0) return false;
        Arrays.sort(a); Arrays.sort(b);
        return Arrays.equals(a, b);
    }
    public static void main(String[] args) {
        System.out.println("listen, silent -> " + is_anagram("listen", "silent"));
        System.out.println("Debit card, Bad credit -> " + is_anagram("Debit card", "Bad credit"));
        System.out.println("hello, world -> " + is_anagram("hello", "world"));
    }
}
