import java.text.SimpleDateFormat;
import java.text.ParseException;

public class PersonnummerValidator {
    public static boolean isValid(String pnr) {
        if (pnr == null || pnr.length() < 10) return false;
        String clean = pnr.replaceAll("[-+*]", "").replaceAll("X", "0");
        if (clean.length() != 10 && clean.length() != 12) return false;
        
        String dPart = clean.length() == 12 ? clean.substring(0, 8) : clean.substring(0, 6);
        
        int d = Integer.parseInt(dPart.substring(dPart.length() - 2));
        boolean isCoord = d > 60;
        
        String dPartNorm = dPart;
        if (isCoord) {
            int normD = d - 60;
            dPartNorm = dPart.substring(0, dPart.length() - 2) + String.format("%02d", normD);
        }
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dPartNorm.length() == 8 ? "yyyyMMdd" : "yyMMdd");
            sdf.setLenient(false);
            sdf.parse(dPartNorm);
        } catch (ParseException e) {
            return false;
        }
        
        if (pnr.contains("X")) return true;
        
        int[] digits = new int[10];
        String tenDigits = clean.length() == 12 ? clean.substring(2) : clean;
        for (int i = 0; i < 10; i++) {
            digits[i] = tenDigits.charAt(i) - '0';
        }
        
        int checksum = 0;
        for (int i = 0; i < 10; i++) {
            int v = digits[i];
            if (i % 2 == 0) v *= 2;
            if (v > 9) v -= 9;
            checksum += v;
        }
        return checksum % 10 == 0;
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
