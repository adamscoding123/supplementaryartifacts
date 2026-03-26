import java.time.YearMonth;
public class PersonnummerValidator {
    public boolean validatePersonnummer(String input) {
        if (input == null || input.isEmpty()) return false;
        boolean centenarian = input.contains("+");
        String nums = input.replaceAll("[^\\d]", "");
        if (nums.length() != 10 && nums.length() != 12) return false;

        int yr, mo, dy;
        if (nums.length() == 12) {
            yr = Integer.parseInt(nums.substring(0, 4));
            mo = Integer.parseInt(nums.substring(4, 6));
            dy = Integer.parseInt(nums.substring(6, 8));
        } else {
            int shortYear = Integer.parseInt(nums.substring(0, 2));
            yr = computeFullYear(shortYear, centenarian);
            mo = Integer.parseInt(nums.substring(2, 4));
            dy = Integer.parseInt(nums.substring(4, 6));
        }

        try {
            if (!YearMonth.of(yr, mo).isValidDay(dy)) return false;
        } catch (Exception e) { return false; }

        String digits10 = nums.length() == 12 ? nums.substring(2) : nums;
        return isLuhnValid(digits10);
    }

    private int computeFullYear(int yy, boolean plus) {
        int now = java.time.LocalDate.now().getYear();
        int century = now - (now % 100);
        int full = century + yy;
        if (full > now) full -= 100;
        if (plus) full -= 100;
        return full;
    }

    private boolean isLuhnValid(String str) {
        int total = 0;
        for (int idx = 0; idx < str.length(); idx++) {
            int d = str.charAt(idx) - '0';
            int multiplier = (idx % 2 == 0) ? 2 : 1;
            int product = d * multiplier;
            total += (product / 10) + (product % 10);
        }
        return total % 10 == 0;
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
