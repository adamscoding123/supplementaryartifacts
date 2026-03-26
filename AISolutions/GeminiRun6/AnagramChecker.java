public class AnagramChecker {
    public boolean isAnagram(String str1, String str2) {
        if (str1 == null || str2 == null) return false;
        String normalized1 = str1.replaceAll("\\s+", "").toLowerCase();
        String normalized2 = str2.replaceAll("\\s+", "").toLowerCase();
        if (normalized1.length() != normalized2.length()) return false;
        int[] freq = new int[128];
        for (int i = 0; i < normalized1.length(); i++) {
            freq[normalized1.charAt(i)]++;
            freq[normalized2.charAt(i)]--;
        }
        for (int f : freq) {
            if (f != 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        AnagramChecker ac = new AnagramChecker();
        System.out.println("listen, silent -> " + ac.isAnagram("listen", "silent")); // true
        System.out.println("Listen, Silent -> " + ac.isAnagram("Listen", "Silent")); // true
        System.out.println("hello, world -> " + ac.isAnagram("hello", "world")); // false
        System.out.println("a b c, cba -> " + ac.isAnagram("a b c", "cba")); // true
        System.out.println("empty -> " + ac.isAnagram("", "")); // true
    }
}
