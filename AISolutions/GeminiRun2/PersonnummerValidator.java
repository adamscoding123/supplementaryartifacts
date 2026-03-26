public class PersonnummerValidator {
    public boolean validatePersonnummer(String pn) {
        if (pn == null || pn.length() < 10) return false;
        boolean hasPlus = pn.contains("+");
        String digits = pn.replace("-", "").replace("+", "");
        if (digits.length() != 10 && digits.length() != 12) return false;
        
        String yyyy, mm, dd;
        if (digits.length() == 12) {
            yyyy = digits.substring(0, 4);
            mm = digits.substring(4, 6);
            dd = digits.substring(6, 8);
        } else {
            int yy = Integer.parseInt(digits.substring(0, 2));
            int currentYear = java.time.Year.now().getValue();
            int century = (currentYear / 100) * 100;
            int year = century + yy;
            if (year > currentYear) year -= 100;
            if (hasPlus) year -= 100;
            yyyy = String.valueOf(year);
            mm = digits.substring(2, 4);
            dd = digits.substring(4, 6);
        }
        if (!isValidDate(Integer.parseInt(yyyy), Integer.parseInt(mm), Integer.parseInt(dd))) return false;
        
        String luhnStr = digits.length() == 12 ? digits.substring(2) : digits;
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            int val = Character.getNumericValue(luhnStr.charAt(i));
            if (i % 2 == 0) val *= 2;
            if (val > 9) val -= 9;
            sum += val;
        }
        return sum % 10 == 0;
    }
    private boolean isValidDate(int y, int m, int d) {
        if (m < 1 || m > 12 || d < 1 || d > 31) return false;
        if (m == 4 || m == 6 || m == 9 || m == 11) return d <= 30;
        if (m == 2) {
            boolean isLeap = (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0);
            return d <= (isLeap ? 29 : 28);
        }
        return true;
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