import java.util.Arrays;

public class AnagramChecker {
    public static boolean is_anagram(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        String clean1 = prepare(s1);
        String clean2 = prepare(s2);
        return clean1.equals(clean2);
    }
    private static String prepare(String s) {
        char[] arr = s.toLowerCase().replace(" ", "").toCharArray();
        Arrays.sort(arr);
        return new String(arr);
    }
    public static void main(String[] args) {
        System.out.println("listen, silent -> " + is_anagram("listen", "silent"));
        System.out.println("Debit card, Bad credit -> " + is_anagram("Debit card", "Bad credit"));
        System.out.println("hello, world -> " + is_anagram("hello", "world"));
    }
}
