import java.util.Arrays;

public class AnagramChecker {
    public static boolean is_anagram(String a, String b) {
        if (a == null || b == null) return false;
        char[] c1 = a.toLowerCase().replaceAll("\\s", "").toCharArray();
        char[] c2 = b.toLowerCase().replaceAll("\\s", "").toCharArray();
        Arrays.sort(c1); Arrays.sort(c2);
        return Arrays.equals(c1, c2);
    }

    public static void main(String[] args) {
        System.out.println("listen, silent -> " + is_anagram("listen", "silent"));
        System.out.println("Debit card, Bad credit -> " + is_anagram("Debit card", "Bad credit"));
        System.out.println("hello, world -> " + is_anagram("hello", "world"));
    }
}
