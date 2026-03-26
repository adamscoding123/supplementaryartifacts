import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class ShoppingCartPriceCalculator {
    public static class Item {
        public double price; public int qty;
        public Item(double p, int q) { price = p; qty = q; }
    }
    public double calculateTotal(List<Item> items) {
        if (items == null || items.isEmpty()) return 0.00;
        BigDecimal sum = BigDecimal.ZERO;
        for (Item item : items) {
            BigDecimal lineTotal = BigDecimal.valueOf(item.price).multiply(BigDecimal.valueOf(item.qty));
            sum = sum.add(lineTotal);
        }
        if (sum.compareTo(BigDecimal.valueOf(500)) > 0) {
            sum = sum.multiply(BigDecimal.valueOf(0.90));
        }
        sum = sum.multiply(BigDecimal.valueOf(1.25));
        return sum.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public static void main(String[] args) {
        ShoppingCartPriceCalculator calc = new ShoppingCartPriceCalculator();
        System.out.println("Basic (100x2) -> " + calc.calculateTotal(java.util.Arrays.asList(new Item(100, 2)))); // 250.0
        System.out.println("Discount (300x2) -> " + calc.calculateTotal(java.util.Arrays.asList(new Item(300, 2)))); // 675.0
        System.out.println("Boundary (250x2) -> " + calc.calculateTotal(java.util.Arrays.asList(new Item(250, 2)))); // 625.0
        System.out.println("Rounding -> " + calc.calculateTotal(java.util.Arrays.asList(new Item(250.5, 2), new Item(0.5, 1)))); // 564.19
        System.out.println("Empty -> " + calc.calculateTotal(new java.util.ArrayList<>())); // 0.0
    }
}
