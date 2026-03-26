import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class PersonnummerValidator {
    public boolean validatePersonnummer(String pn) {
        if (pn == null) return false;
        String digits = pn.replaceAll("[^0-9+]", "");
        if (digits.length() != 10 && digits.length() != 12) return false;
        boolean plusSign = pn.contains("+");
        String dateStr;
        if (digits.length() == 12) {
            dateStr = digits.substring(0, 8);
        } else {
            int currentYear = LocalDate.now().getYear();
            int century = (currentYear / 100) * 100;
            int twoDigitYear = Integer.parseInt(digits.substring(0, 2));
            int fullYear = century + twoDigitYear;
            if (fullYear > currentYear) fullYear -= 100;
            if (plusSign) fullYear -= 100;
            dateStr = String.format("%04d%s", fullYear, digits.substring(2, 6));
        }
        if (!isDateValid(dateStr)) return false;
        String luhnDigits = digits.length() == 12 ? digits.substring(2) : digits;
        return luhnCheck(luhnDigits);
    }
    private boolean isDateValid(String dateStr) {
        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyyMMdd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    private boolean luhnCheck(String s) {
        int total = 0;
        boolean doubleIt = false;
        for (int i = s.length() - 1; i >= 0; i--) {
            int n = s.charAt(i) - '0';
            if (doubleIt) {
                n *= 2;
                if (n > 9) n = (n % 10) + 1;
            }
            total += n;
            doubleIt = !doubleIt;
        }
        return (total % 10 == 0);
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
