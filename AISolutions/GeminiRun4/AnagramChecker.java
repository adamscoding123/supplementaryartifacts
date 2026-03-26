import java.util.stream.Collectors;
public class AnagramChecker {
    public boolean isAnagram(String str1, String str2) {
        if (str1 == null || str2 == null) return false;
        String clean1 = str1.chars().filter(c -> c != ' ').map(Character::toLowerCase)
                            .sorted().mapToObj(c -> String.valueOf((char)c)).collect(Collectors.joining());
        String clean2 = str2.chars().filter(c -> c != ' ').map(Character::toLowerCase)
                            .sorted().mapToObj(c -> String.valueOf((char)c)).collect(Collectors.joining());
        return clean1.equals(clean2);
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