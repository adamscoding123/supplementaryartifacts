import java.util.Arrays;

public class AnagramChecker {
    public static boolean is_anagram(String str1, String str2) {
        if (str1 == null || str2 == null) return false;
        String s1 = str1.replaceAll("\\s", "").toLowerCase();
        String s2 = str2.replaceAll("\\s", "").toLowerCase();
        if (s1.length() != s2.length()) return false;
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        return Arrays.equals(arr1, arr2);
    }

    public static void main(String[] args) {
        System.out.println("listen, silent -> " + is_anagram("listen", "silent")); // true
        System.out.println("Debit card, Bad credit -> " + is_anagram("Debit card", "Bad credit")); // true
        System.out.println("hello, world -> " + is_anagram("hello", "world")); // false
    }
}
