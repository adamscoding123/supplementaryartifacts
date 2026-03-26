import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class PersonnummerValidator {
    public boolean validatePersonnummer(String pn) {
        if (pn == null) return false;
        String clean = pn.replaceAll("[^0-9+]", "");
        if (clean.length() != 10 && clean.length() != 12) return false;
        boolean isPlus = pn.contains("+");
        String datePart = clean.length() == 12 ? clean.substring(0, 8) : "";
        if (clean.length() == 10) {
            int yearOffset = isPlus ? 100 : 0;
            int currentYear = LocalDate.now().getYear();
            int currentCentury = (currentYear / 100) * 100;
            int year2Digit = Integer.parseInt(clean.substring(0, 2));
            int fullYear = currentCentury + year2Digit;
            if (fullYear > currentYear) fullYear -= 100;
            fullYear -= yearOffset;
            datePart = String.format("%04d%s", fullYear, clean.substring(2, 6));
        }
        try {
            LocalDate.parse(datePart, DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (DateTimeParseException e) {
            return false;
        }
        String luhnPart = clean.length() == 12 ? clean.substring(2) : clean;
        return checkLuhn(luhnPart);
    }
    private boolean checkLuhn(String s) {
        int sum = 0;
        boolean alternate = false;
        for (int i = s.length() - 1; i >= 0; i--) {
            int n = s.charAt(i) - '0';
            if (alternate) {
                n *= 2;
                if (n > 9) n = (n % 10) + 1;
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
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