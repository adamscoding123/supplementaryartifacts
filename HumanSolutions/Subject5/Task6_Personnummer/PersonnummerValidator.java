import java.util.Calendar;
import java.util.GregorianCalendar;

public class PersonnummerValidator {
    public static boolean isValid(String pnr) {
        if (pnr == null) return false;

        String separator = "";
        if (pnr.contains("+")) separator = "+";
        else if (pnr.contains("-")) separator = "-";

        String cleaned = pnr.replace("*", "");
        String digits = cleaned.replaceAll("[-+]", "");
        boolean hasX = digits.contains("X");
        digits = digits.replace("X", "0");

        if (digits.length() != 10 && digits.length() != 12) return false;
        for (char c : digits.toCharArray()) {
            if (c < '0' || c > '9') return false;
        }

        int year, month, day;
        String lastFour;

        if (digits.length() == 12) {
            year = Integer.parseInt(digits.substring(0, 4));
            month = Integer.parseInt(digits.substring(4, 6));
            day = Integer.parseInt(digits.substring(6, 8));
            lastFour = digits.substring(8);
        } else {
            int yy = Integer.parseInt(digits.substring(0, 2));
            month = Integer.parseInt(digits.substring(2, 4));
            day = Integer.parseInt(digits.substring(4, 6));
            lastFour = digits.substring(6);

            Calendar cal = Calendar.getInstance();
            int thisYear = cal.get(Calendar.YEAR);
            int cent = (thisYear / 100) * 100;
            year = cent + yy;
            if (year > thisYear) year -= 100;
            if (separator.equals("+")) year -= 100;
        }

        if (day > 60) day -= 60;

        GregorianCalendar gc = new GregorianCalendar();
        gc.setLenient(false);
        gc.set(year, month - 1, day);
        try {
            gc.getTime();
        } catch (Exception e) {
            return false;
        }

        if (hasX) return true;

        String forLuhn;
        if (digits.length() == 12) {
            forLuhn = digits.substring(2);
        } else {
            forLuhn = digits;
        }

        int sum = 0;
        boolean doubleIt = true;
        for (int i = 0; i < forLuhn.length(); i++) {
            int val = forLuhn.charAt(i) - '0';
            if (doubleIt) {
                val = val * 2;
                if (val > 9) val = val - 9;
            }
            sum += val;
            doubleIt = !doubleIt;
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
