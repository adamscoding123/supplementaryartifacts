public class AnagramChecker {
    public static boolean is_anagram(String str1, String str2) {
        if (str1 == null || str2 == null) return false;
        int[] counts = new int[256];
        int len1 = 0, len2 = 0;
        for (char c : str1.toLowerCase().toCharArray()) {
            if (c != ' ' && c < 256) { counts[c]++; len1++; }
        }
        for (char c : str2.toLowerCase().toCharArray()) {
            if (c != ' ' && c < 256) { counts[c]--; len2++; }
        }
        if (len1 != len2) return false;
        for (int count : counts) {
            if (count != 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("listen, silent -> " + is_anagram("listen", "silent")); // true
        System.out.println("Debit card, Bad credit -> " + is_anagram("Debit card", "Bad credit")); // true
        System.out.println("hello, world -> " + is_anagram("hello", "world")); // false
    }
}