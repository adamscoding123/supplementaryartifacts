import java.time.LocalDate;

public class PersonnummerValidator {
    public static boolean isValid(String pnr) {
        if (pnr == null) return false;

        boolean centuryPlus = pnr.contains("+");
        boolean xPlaceholder = pnr.contains("X");

        String withoutSep = pnr.replaceAll("[-+*]", "");
        String normalized = withoutSep.replace("X", "0");

        boolean is12;
        if (normalized.length() == 12) {
            is12 = true;
        } else if (normalized.length() == 10) {
            is12 = false;
        } else {
            return false;
        }

        for (int i = 0; i < normalized.length(); i++) {
            if (!Character.isDigit(normalized.charAt(i))) return false;
        }

        int y, m, d;
        if (is12) {
            y = grab(normalized, 0, 4);
            m = grab(normalized, 4, 6);
            d = grab(normalized, 6, 8);
        } else {
            int yy = grab(normalized, 0, 2);
            m = grab(normalized, 2, 4);
            d = grab(normalized, 4, 6);
            int thisYear = LocalDate.now().getYear();
            y = thisYear - (thisYear % 100) + yy;
            if (y > thisYear) y -= 100;
            if (centuryPlus) y -= 100;
        }

        if (d > 60) d -= 60;

        try {
            LocalDate.of(y, m, d);
        } catch (Exception ex) {
            return false;
        }

        if (xPlaceholder) return true;

        String luhnInput;
        if (is12) {
            luhnInput = normalized.substring(2);
        } else {
            luhnInput = normalized;
        }

        if (luhnInput.length() != 10) return false;

        int[] arr = new int[10];
        for (int i = 0; i < 10; i++) arr[i] = luhnInput.charAt(i) - '0';

        int total = 0;
        for (int i = 0; i < arr.length; i++) {
            int n = arr[i];
            if (i % 2 == 0) n *= 2;
            total += n / 10 + n % 10;
        }
        return total % 10 == 0;
    }

    private static int grab(String s, int start, int end) {
        return Integer.parseInt(s.substring(start, end));
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
