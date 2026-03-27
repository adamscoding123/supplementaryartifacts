import java.time.LocalDate;
import java.time.YearMonth;

public class PersonnummerValidator {
    public static boolean isValid(String pnr) {
        if (pnr == null) return false;
        String trimmed = pnr.trim();
        if (trimmed.isEmpty()) return false;

        boolean plusSign = trimmed.contains("+");
        boolean hasPlaceholder = trimmed.contains("X");

        String raw = trimmed.replaceAll("[\\-+*]", "").replace("X", "0");
        if (raw.length() != 10 && raw.length() != 12) return false;

        for (int i = 0; i < raw.length(); i++) {
            if (!Character.isDigit(raw.charAt(i))) return false;
        }

        int fullYear, month, day;

        if (raw.length() == 12) {
            fullYear = Integer.parseInt(raw.substring(0, 4));
            month = Integer.parseInt(raw.substring(4, 6));
            day = Integer.parseInt(raw.substring(6, 8));
        } else {
            int yy = Integer.parseInt(raw.substring(0, 2));
            month = Integer.parseInt(raw.substring(2, 4));
            day = Integer.parseInt(raw.substring(4, 6));
            int currentYear = LocalDate.now().getYear();
            fullYear = (currentYear / 100) * 100 + yy;
            if (fullYear > currentYear) fullYear -= 100;
            if (plusSign) fullYear -= 100;
        }

        int actualDay = day > 60 ? day - 60 : day;

        if (month < 1 || month > 12) return false;
        YearMonth ym = YearMonth.of(fullYear, month);
        if (actualDay < 1 || actualDay > ym.lengthOfMonth()) return false;

        if (hasPlaceholder) return true;

        String ten = raw.length() == 12 ? raw.substring(2) : raw;
        return computeLuhn(ten);
    }

    private static boolean computeLuhn(String number) {
        int[] digits = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            digits[i] = number.charAt(i) - '0';
        }
        int sum = 0;
        for (int i = 0; i < digits.length; i++) {
            int d = digits[i];
            if (i % 2 == 0) {
                d *= 2;
                if (d >= 10) {
                    d = (d / 10) + (d % 10);
                }
            }
            sum += d;
        }
        return sum % 10 == 0;
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
