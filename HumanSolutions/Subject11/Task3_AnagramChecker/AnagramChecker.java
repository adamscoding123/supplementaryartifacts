public class AnagramChecker {
    public static boolean is_anagram(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        String a = s1.toLowerCase().replace(" ", "");
        String b = s2.toLowerCase().replace(" ", "");
        if (a.length() != b.length()) return false;
        int[] counts = new int[256];
        for (int i = 0; i < a.length(); i++) counts[a.charAt(i)]++;
        for (int i = 0; i < b.length(); i++) {
            if (--counts[b.charAt(i)] < 0) return false;
        }
        return true;
    }
    public static void main(String[] args) {
        System.out.println("listen, silent -> " + is_anagram("listen", "silent"));
        System.out.println("Debit card, Bad credit -> " + is_anagram("Debit card", "Bad credit"));
        System.out.println("hello, world -> " + is_anagram("hello", "world"));
    }
}
