import java.util.Arrays;

public class AnagramChecker {
    public static boolean is_anagram(String str1, String str2) {
        if (str1 == null || str2 == null) return false;
        
        String a = str1.toLowerCase().replace(" ", "");
        String b = str2.toLowerCase().replace(" ", "");
        
        if (a.length() != b.length()) return false;
        
        return sortString(a).equals(sortString(b));
    }
    
    private static String sortString(String input) {
        char[] arr = input.toCharArray();
        Arrays.sort(arr);
        return String.valueOf(arr);
    }

    public static void main(String[] args) {
        System.out.println("listen, silent -> " + is_anagram("listen", "silent"));
        System.out.println("Debit card, Bad credit -> " + is_anagram("Debit card", "Bad credit"));
        System.out.println("hello, world -> " + is_anagram("hello", "world"));
    }
}
