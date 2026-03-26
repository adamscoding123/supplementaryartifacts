import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class PersonnummerValidator {
    public boolean validatePersonnummer(String pn) {
        if (pn == null) return false;
        String raw = pn.replaceAll("[^0-9+]", "");
        boolean usePlus = pn.contains("+");
        if (raw.length() != 10 && raw.length() != 12) return false;
        String datePortion;
        if (raw.length() == 12) {
            datePortion = raw.substring(0, 8);
        } else {
            int yr = Integer.parseInt(raw.substring(0, 2));
            int thisYear = LocalDate.now().getYear();
            int cent = (thisYear / 100) * 100;
            int resolved = cent + yr;
            if (resolved > thisYear) resolved -= 100;
            if (usePlus) resolved -= 100;
            datePortion = String.format("%04d%s", resolved, raw.substring(2, 6));
        }
        try {
            LocalDate.parse(datePortion, DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (DateTimeParseException e) {
            return false;
        }
        String tenDigits = raw.length() == 12 ? raw.substring(2) : raw;
        return verifyLuhn(tenDigits);
    }
    private boolean verifyLuhn(String s) {
        int sum = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            int val = s.charAt(i) - '0';
            if ((s.length() - 1 - i) % 2 == 1) {
                val *= 2;
                if (val > 9) val = val - 9;
            }
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
