import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonnummerValidator {
    private static final Pattern PNR_RE = Pattern.compile(
            "^(\\d{2,4})(\\d{2})(\\d{2})([-+]?)([\\dX]{4})\\*?$");

    public static boolean isValid(String pnr) {
        if (pnr == null) return false;
        Matcher mat = PNR_RE.matcher(pnr);
        if (!mat.matches()) return false;

        String yearStr = mat.group(1);
        int month = Integer.parseInt(mat.group(2));
        int day = Integer.parseInt(mat.group(3));
        String sep = mat.group(4);
        String last4 = mat.group(5);

        int year;
        boolean is12Digit = yearStr.length() == 4;

        if (is12Digit) {
            year = Integer.parseInt(yearStr);
        } else {
            int yy = Integer.parseInt(yearStr);
            GregorianCalendar now = new GregorianCalendar();
            int currentYear = now.get(GregorianCalendar.YEAR);
            year = (currentYear / 100) * 100 + yy;
            if (year > currentYear) year -= 100;
            if ("+".equals(sep)) year -= 100;
        }

        int checkDay = day;
        if (checkDay > 60) checkDay -= 60;

        if (!dateExists(year, month, checkDay)) return false;

        if (last4.contains("X")) return true;

        String allDigits = pnr.replaceAll("[^0-9]", "");
        if (allDigits.length() != 10 && allDigits.length() != 12) return false;

        String forCheck = allDigits.length() == 12 ? allDigits.substring(2) : allDigits;
        return luhnOk(forCheck);
    }

    private static boolean dateExists(int year, int month, int day) {
        if (month < 1 || month > 12 || day < 1) return false;
        GregorianCalendar gc = new GregorianCalendar(year, month - 1, 1);
        return day <= gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
    }

    private static boolean luhnOk(String digits) {
        if (digits.length() != 10) return false;
        int acc = 0;
        for (int pos = 0; pos < 10; pos++) {
            int d = digits.charAt(pos) - '0';
            if (pos % 2 == 0) {
                d += d;
                if (d > 9) d -= 9;
            }
            acc += d;
        }
        return acc % 10 == 0;
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
