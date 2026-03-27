import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonnummerValidator {
    private static final Pattern PATTERN_12 = Pattern.compile("^(\\d{4})(\\d{2})(\\d{2})([-+]?)([\\dX]{4})\\*?$");
    private static final Pattern PATTERN_10 = Pattern.compile("^(\\d{2})(\\d{2})(\\d{2})([-+])([\\dX]{4})\\*?$");

    public static boolean isValid(String pnr) {
        if (pnr == null) return false;

        Matcher m12 = PATTERN_12.matcher(pnr);
        Matcher m10 = PATTERN_10.matcher(pnr);

        int year, month, day;
        String birthNum;
        boolean hasPlus = pnr.contains("+");

        if (m12.matches()) {
            year = Integer.parseInt(m12.group(1));
            month = Integer.parseInt(m12.group(2));
            day = Integer.parseInt(m12.group(3));
            birthNum = m12.group(5);
        } else if (m10.matches()) {
            int shortYear = Integer.parseInt(m10.group(1));
            month = Integer.parseInt(m10.group(2));
            day = Integer.parseInt(m10.group(3));
            birthNum = m10.group(5);

            int currentYear = java.time.LocalDate.now().getYear();
            int century = currentYear / 100;
            year = century * 100 + shortYear;
            if (year > currentYear) {
                year -= 100;
            }
            if (hasPlus) {
                year -= 100;
            }
        } else {
            return false;
        }

        if (day > 60) day -= 60;

        if (!isValidDate(year, month, day)) return false;

        if (birthNum.contains("X")) return true;

        String raw = pnr.replaceAll("[-+*]", "");
        String tenDigit = raw.length() == 12 ? raw.substring(2) : raw;
        return luhn(tenDigit);
    }

    private static boolean isValidDate(int y, int m, int d) {
        if (m < 1 || m > 12 || d < 1) return false;
        int[] daysInMonth = {31, isLeapYear(y) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return d <= daysInMonth[m - 1];
    }

    private static boolean isLeapYear(int y) {
        return (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0);
    }

    private static boolean luhn(String digits) {
        int total = 0;
        for (int i = 0; i < digits.length(); i++) {
            int n = digits.charAt(i) - '0';
            if (i % 2 == 0) {
                n *= 2;
                if (n > 9) n -= 9;
            }
            total += n;
        }
        return total % 10 == 0;
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
