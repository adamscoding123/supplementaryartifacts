public class PersonnummerValidator {
    public static boolean isValid(String pnr) {
        if (pnr == null || pnr.isEmpty()) return false;

        boolean plus = pnr.indexOf('+') >= 0;
        String s = pnr.replaceAll("[^\\dX]", "");
        if (s.length() != 10 && s.length() != 12) return false;

        boolean placeholder = s.contains("X");
        s = s.replace("X", "0");

        int yr, mo, dy;
        if (s.length() == 12) {
            yr = num(s, 0, 4);
            mo = num(s, 4, 6);
            dy = num(s, 6, 8);
        } else {
            int shortYr = num(s, 0, 2);
            mo = num(s, 2, 4);
            dy = num(s, 4, 6);

            int now = java.time.Year.now().getValue();
            yr = (now / 100) * 100 + shortYr;
            if (yr > now) yr -= 100;
            if (plus) yr -= 100;
        }

        if (dy > 60) dy -= 60;

        if (mo < 1 || mo > 12) return false;
        if (dy < 1 || dy > maxDay(yr, mo)) return false;

        if (placeholder) return true;

        return checkLuhn(s.length() == 12 ? s.substring(2) : s);
    }

    private static int num(String s, int from, int to) {
        return Integer.parseInt(s.substring(from, to));
    }

    private static int maxDay(int year, int month) {
        switch (month) {
            case 2:
                if (year % 400 == 0) return 29;
                if (year % 100 == 0) return 28;
                if (year % 4 == 0) return 29;
                return 28;
            case 4: case 6: case 9: case 11:
                return 30;
            default:
                return 31;
        }
    }

    private static boolean checkLuhn(String d) {
        int s = 0;
        for (int i = 0; i < 10; i++) {
            int v = d.charAt(i) - 48;
            if (i % 2 == 0) v <<= 1;
            s += (v > 9) ? v - 9 : v;
        }
        return s % 10 == 0;
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
