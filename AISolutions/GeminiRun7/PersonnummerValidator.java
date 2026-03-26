import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.LocalDate;
public class PersonnummerValidator {
    public boolean validatePersonnummer(String pn) {
        if (pn == null) return false;
        String cleaned = pn.replaceAll("[^0-9]", "");
        boolean hasPlusSign = pn.contains("+");
        if (cleaned.length() != 10 && cleaned.length() != 12) return false;

        String dateString;
        if (cleaned.length() == 12) {
            dateString = cleaned.substring(0, 8);
        } else {
            dateString = resolveCentury(cleaned.substring(0, 2), hasPlusSign) + cleaned.substring(2, 6);
        }
        if (!parseDate(dateString)) return false;

        String forLuhn = cleaned.length() == 12 ? cleaned.substring(2) : cleaned;
        return luhnAlgorithm(forLuhn);
    }
    private String resolveCentury(String yy, boolean plus) {
        int y = Integer.parseInt(yy);
        int now = LocalDate.now().getYear();
        int base = (now / 100) * 100 + y;
        if (base > now) base -= 100;
        if (plus) base -= 100;
        return String.format("%04d", base);
    }
    private boolean parseDate(String d) {
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("uuuuMMdd").withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(d, fmt);
            return true;
        } catch (Exception e) { return false; }
    }
    private boolean luhnAlgorithm(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            int digit = s.charAt(i) - '0';
            if (i % 2 == 0) { digit *= 2; if (digit > 9) digit -= 9; }
            sum += digit;
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
