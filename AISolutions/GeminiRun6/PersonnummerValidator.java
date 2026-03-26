public class PersonnummerValidator {
    public boolean validatePersonnummer(String pn) {
        if (pn == null || pn.length() < 10) return false;
        boolean hasPlus = pn.contains("+");
        String digits = pn.replace("-", "").replace("+", "");
        if (digits.length() != 10 && digits.length() != 12) return false;

        int year, month, day;
        if (digits.length() == 12) {
            year = Integer.parseInt(digits.substring(0, 4));
            month = Integer.parseInt(digits.substring(4, 6));
            day = Integer.parseInt(digits.substring(6, 8));
        } else {
            int yy = Integer.parseInt(digits.substring(0, 2));
            int thisYear = java.time.Year.now().getValue();
            int base = (thisYear / 100) * 100;
            year = base + yy;
            if (year > thisYear) year -= 100;
            if (hasPlus) year -= 100;
            month = Integer.parseInt(digits.substring(2, 4));
            day = Integer.parseInt(digits.substring(4, 6));
        }
        if (!isDateValid(year, month, day)) return false;

        String luhnStr = digits.length() == 12 ? digits.substring(2) : digits;
        return checkLuhn(luhnStr);
    }
    private boolean isDateValid(int y, int m, int d) {
        if (m < 1 || m > 12 || d < 1) return false;
        int[] daysInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if ((y % 4 == 0 && y % 100 != 0) || (y % 400 == 0)) daysInMonth[2] = 29;
        return d <= daysInMonth[m];
    }
    private boolean checkLuhn(String s) {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            int val = s.charAt(i) - '0';
            if (i % 2 == 0) val *= 2;
            if (val > 9) val -= 9;
            sum += val;
        }
        return sum % 10 == 0;
    }

    public static void main(String[] args) {
        PersonnummerValidator pv = new PersonnummerValidator();
        System.out.println("19811218-9876 -> " + pv.validatePersonnummer("19811218-9876")); // true
        System.out.println("811218-9876 -> " + pv.validatePersonnummer("811218-9876")); // true
        System.out.println("121212+1212 -> " + pv.validatePersonnummer("121212+1212")); // true
        System.out.println("20240229-0007 -> " + pv.validatePersonnummer("20240229-0007")); // true
        System.out.println("20230229-0008 -> " + pv.validatePersonnummer("20230229-0008")); // false
        System.out.println("811218-9877 -> " + pv.validatePersonnummer("811218-9877")); // false
    }
}
