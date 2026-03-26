public class AnagramChecker {
    public boolean isAnagram(String str1, String str2) {
        if (str1 == null || str2 == null) return false;
        String s1 = str1.toLowerCase().replaceAll("\\s", "");
        String s2 = str2.toLowerCase().replaceAll("\\s", "");
        if (s1.length() != s2.length()) return false;
        int[] charFreq = new int[256];
        for (int i = 0; i < s1.length(); i++) {
            charFreq[s1.charAt(i)]++;
        }
        for (int i = 0; i < s2.length(); i++) {
            charFreq[s2.charAt(i)]--;
            if (charFreq[s2.charAt(i)] < 0) return false;
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
