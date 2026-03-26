import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.LocalDate;
public class PersonnummerValidator {
    public boolean validatePersonnummer(String pn) {
        if (pn == null) return false;
        if (!pn.matches("^\\\d{6}[+-]\\d{4}$") && !pn.matches("^\\\d{8}[-]\\d{4}$") && !pn.matches("^\\\d{10}$") && !pn.matches("^\\\d{12}$")) return false;
        String normalized = pn.replace("-", "").replace("+", "");
        if (normalized.length() != 10 && normalized.length() != 12) return false;
        
        String dateStr = normalized.length() == 12 ? normalized.substring(0, 8) : convertTo12(pn);
        if (dateStr == null || !isValidDate(dateStr)) return false;
        
        String toCheck = normalized.length() == 12 ? normalized.substring(2) : normalized;
        return calculateLuhn(toCheck);
    }
    private String convertTo12(String pn) {
        String digits = pn.replace("-", "").replace("+", "");
        int y = Integer.parseInt(digits.substring(0, 2));
        int curr = LocalDate.now().getYear();
        int base = (curr / 100) * 100 + y;
        if (base > curr) base -= 100;
        if (pn.contains("+")) base -= 100;
        return String.format("%04d%s", base, digits.substring(2, 6));
    }
    private boolean isValidDate(String dateStr) {
        try {
            DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuuMMdd").withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(dateStr, f);
            return true;
        } catch (Exception e) { return false; }
    }
    private boolean calculateLuhn(String s) {
        int sum = 0;
        for(int i = 0; i < s.length(); i++) {
            int d = s.charAt(i) - '0';
            if (i % 2 == 0) { d *= 2; if (d > 9) d -= 9; }
            sum += d;
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