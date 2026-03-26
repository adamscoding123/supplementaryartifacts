public class PersonnummerValidator {
    public boolean validatePersonnummer(String pn) {
        if (pn == null || pn.length() < 10) return false;
        boolean plusPresent = pn.contains("+");
        String digits = pn.replaceAll("[^0-9]", "");
        if (digits.length() != 10 && digits.length() != 12) return false;

        int year, month, day;
        if (digits.length() == 12) {
            year = Integer.parseInt(digits.substring(0, 4));
            month = Integer.parseInt(digits.substring(4, 6));
            day = Integer.parseInt(digits.substring(6, 8));
        } else {
            int yy = Integer.parseInt(digits.substring(0, 2));
            int currentYr = java.time.Year.now().getValue();
            int century = (currentYr / 100) * 100;
            year = century + yy;
            if (year > currentYr) year -= 100;
            if (plusPresent) year -= 100;
            month = Integer.parseInt(digits.substring(2, 4));
            day = Integer.parseInt(digits.substring(4, 6));
        }
        if (!dateIsValid(year, month, day)) return false;

        String forLuhn = digits.length() == 12 ? digits.substring(2) : digits;
        int luhnSum = 0;
        for (int i = 0; i < 10; i++) {
            int d = forLuhn.charAt(i) - '0';
            if (i % 2 == 0) d *= 2;
            if (d > 9) d -= 9;
            luhnSum += d;
        }
        return luhnSum % 10 == 0;
    }
    private boolean dateIsValid(int y, int m, int d) {
        if (m < 1 || m > 12 || d < 1 || d > 31) return false;
        boolean leap = (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0);
        int maxDay;
        switch (m) {
            case 2: maxDay = leap ? 29 : 28; break;
            case 4: case 6: case 9: case 11: maxDay = 30; break;
            default: maxDay = 31;
        }
        return d <= maxDay;
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
