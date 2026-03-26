import java.time.YearMonth;
public class PersonnummerValidator {
    public boolean validatePersonnummer(String input) {
        if (input == null || input.isEmpty()) return false;
        boolean isCentenarian = input.contains("+");
        String cleaned = input.replaceAll("[^\\d]", "");
        if (cleaned.length() != 10 && cleaned.length() != 12) return false;
        
        int year, month, day;
        if (cleaned.length() == 12) {
            year = Integer.parseInt(cleaned.substring(0, 4));
            month = Integer.parseInt(cleaned.substring(4, 6));
            day = Integer.parseInt(cleaned.substring(6, 8));
        } else {
            int yy = Integer.parseInt(cleaned.substring(0, 2));
            year = resolveYear(yy, isCentenarian);
            month = Integer.parseInt(cleaned.substring(2, 4));
            day = Integer.parseInt(cleaned.substring(4, 6));
        }
        
        try {
            if (!YearMonth.of(year, month).isValidDay(day)) return false;
        } catch (Exception e) { return false; }
        
        String luhnTarget = cleaned.length() == 12 ? cleaned.substring(2) : cleaned;
        return validateLuhn(luhnTarget);
    }
    
    private int resolveYear(int yy, boolean isCentenarian) {
        int currentYear = java.time.LocalDate.now().getYear();
        int currentCentury = currentYear - (currentYear % 100);
        int candidate = currentCentury + yy;
        if (candidate > currentYear) candidate -= 100;
        if (isCentenarian) candidate -= 100;
        return candidate;
    }
    
    private boolean validateLuhn(String str) {
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            int digit = str.charAt(i) - '0';
            int weight = (i % 2 == 0) ? 2 : 1;
            int prod = digit * weight;
            sum += (prod / 10) + (prod % 10);
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