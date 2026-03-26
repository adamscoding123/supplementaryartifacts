import java.util.Arrays;
public class AnagramChecker {
    public boolean isAnagram(String str1, String str2) {
        if (str1 == null || str2 == null) return false;
        String clean1 = str1.toLowerCase().replaceAll(" ", "");
        String clean2 = str2.toLowerCase().replaceAll(" ", "");
        char[] sorted1 = clean1.toCharArray();
        char[] sorted2 = clean2.toCharArray();
        Arrays.sort(sorted1);
        Arrays.sort(sorted2);
        return new String(sorted1).equals(new String(sorted2));
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
