import java.time.LocalDate;

public class PersonnummerValidator {
    public static boolean isValid(String pnr) {
        if (pnr == null || !pnr.matches("^(\\d{6}|\\d{8})[-+*]?[\\dX]{4}\\*?$")) return false;
        
        String clean = pnr.replaceAll("[-+*]", "");
        String datePart = clean.length() == 12 ? clean.substring(0, 8) : clean.substring(0, 6);
        
        int y, m, d;
        if (datePart.length() == 8) {
            y = Integer.parseInt(datePart.substring(0, 4));
            m = Integer.parseInt(datePart.substring(4, 6));
            d = Integer.parseInt(datePart.substring(6, 8));
        } else {
            y = Integer.parseInt(datePart.substring(0, 2));
            m = Integer.parseInt(datePart.substring(2, 4));
            d = Integer.parseInt(datePart.substring(4, 6));
            
            int curYear = LocalDate.now().getYear();
            int century = (curYear / 100) * 100;
            y += century;
            if (y > curYear || pnr.contains("+")) y -= 100;
        }
        
        if (d >= 61 && d <= 91) d -= 60;
        
        try {
            LocalDate.of(y, m, d);
        } catch (Exception e) {
            return false;
        }
        
        if (pnr.contains("X")) return true;
        
        String nineDigits = clean.length() == 12 ? clean.substring(2, 11) : clean.substring(0, 9);
        int expectedLuhn = calculateLuhn(nineDigits);
        return expectedLuhn == (clean.charAt(clean.length() - 1) - '0');
    }
    
    private static int calculateLuhn(String str) {
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            int digit = str.charAt(i) - '0';
            if (i % 2 == 0) digit *= 2;
            if (digit > 9) digit -= 9;
            sum += digit;
        }
        return (10 - (sum % 10)) % 10;
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
