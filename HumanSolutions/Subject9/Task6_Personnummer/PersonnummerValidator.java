import java.time.LocalDate;
import java.util.stream.IntStream;

public class PersonnummerValidator {
    public static boolean isValid(String pnr) {
        if (pnr == null || pnr.length() < 10) return false;

        boolean plus = pnr.contains("+");
        boolean hasX = pnr.contains("X");

        String stripped = pnr.replaceAll("[^\\dX]", "");
        String nums = stripped.replace("X", "0");

        if (nums.length() != 10 && nums.length() != 12) return false;

        for (int i = 0; i < nums.length(); i++) {
            if (!Character.isDigit(nums.charAt(i))) return false;
        }

        int y, m, d;
        boolean longFormat = nums.length() == 12;

        if (longFormat) {
            y = Integer.parseInt(nums.substring(0, 4));
            m = Integer.parseInt(nums.substring(4, 6));
            d = Integer.parseInt(nums.substring(6, 8));
        } else {
            int yy = Integer.parseInt(nums.substring(0, 2));
            m = Integer.parseInt(nums.substring(2, 4));
            d = Integer.parseInt(nums.substring(4, 6));

            int now = LocalDate.now().getYear();
            y = now - now % 100 + yy;
            if (y > now) y -= 100;
            if (plus) y -= 100;
        }

        int realDay = d > 60 ? d - 60 : d;

        try {
            LocalDate.of(y, m, realDay);
        } catch (java.time.DateTimeException e) {
            return false;
        }

        if (hasX) return true;

        String tenDigits = longFormat ? nums.substring(2) : nums;
        int checksum = IntStream.range(0, 10)
                .map(i -> {
                    int digit = tenDigits.charAt(i) - '0';
                    if (i % 2 == 0) digit *= 2;
                    return digit > 9 ? digit - 9 : digit;
                })
                .sum();

        return checksum % 10 == 0;
    }

    public static void main(String[] args) {
        System.out.println("19811218-9876 -> " + isValid("19811218-9876")); // true
        System.out.println("811218-9876 -> " + isValid("811218-9876")); // true
        System.out.println("121212+1212 -> " + isValid("121212+1212")); // true
        System.out.println("20240229-XXXX* -> " + isValid("20240229-XXXX*")); // true
        System.out.println("20230229-XXXX* -> " + isValid("20230229-XXXX*")); // false
        System.out.println("811218-9877 -> " + isValid("811218-9877")); // false
    }
}
