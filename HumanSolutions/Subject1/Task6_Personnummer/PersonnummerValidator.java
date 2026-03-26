import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class PersonnummerValidator {
    public static boolean isValid(String pnr) {
        if (pnr == null) return false;
        
        // Remove valid separators and asterisk
        String digitsOnly = pnr.replaceAll("[-+*]", ""); 
        digitsOnly = digitsOnly.replaceAll("X", "0"); // Replace X with 0 to bypass digit parsing

        if (digitsOnly.length() != 10 && digitsOnly.length() != 12) {
            return false;
        }
        
        String datePart = "";
        
        if (digitsOnly.length() == 12) {
            datePart = digitsOnly.substring(0, 8);
        } else {
            datePart = digitsOnly.substring(0, 6);
        }
        
        // Date validation
        try {
            if (datePart.length() == 8) {
                DateTimeFormatter f = DateTimeFormatter.ofPattern("uuuuMMdd").withResolverStyle(ResolverStyle.STRICT);
                LocalDate.parse(datePart, f);
            } else {
                int y = Integer.parseInt(datePart.substring(0, 2));
                int m = Integer.parseInt(datePart.substring(2, 4));
                int d = Integer.parseInt(datePart.substring(4, 6));
                
                boolean isCoord = d >= 61 && d <= 91;
                if (isCoord) d -= 60;
                
                if (m < 1 || m > 12) return false;
                
                int currentYear = LocalDate.now().getYear();
                int century = (currentYear / 100) * 100;
                int fullYear = century + y;
                if (fullYear > currentYear) {
                    fullYear -= 100;
                }
                if (pnr.contains("+")) {
                    fullYear -= 100;
                }
                
                LocalDate.of(fullYear, m, d);
            }
        } catch (Exception e) {
            return false;
        }
        
        // Skip luhn check if it contains 'X' indicating placeholder
        if (pnr.contains("X")) {
            return true;
        }

        // Luhn Check
        String tenDigits = digitsOnly;
        if (digitsOnly.length() == 12) {
            tenDigits = digitsOnly.substring(2);
        }
        
        return checkLuhn(tenDigits);
    }
    
    private static boolean checkLuhn(String digits) {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            int d = digits.charAt(i) - '0';
            if (i % 2 == 0) {
                d *= 2;
                if (d > 9) d -= 9;
            }
            sum += d;
        }
        return sum % 10 == 0;
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
