import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ShoppingCartPriceCalculator {
    public static class Item {
        public double price;
        public int quantity;
        public Item(double price, int quantity) { this.price = price; this.quantity = quantity; }
    }

    public static double calculate_total(List<Item> items) {
        if (items == null) return 0.0;
        BigDecimal total = BigDecimal.ZERO;
        for (Item item : items) {
            total = total.add(BigDecimal.valueOf(item.price).multiply(BigDecimal.valueOf(item.quantity)));
        }
        if (total.compareTo(BigDecimal.valueOf(500.0)) > 0) {
            total = total.multiply(BigDecimal.valueOf(0.90));
        }
        total = total.multiply(BigDecimal.valueOf(1.25));
        return total.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Item(200, 2)); // 400
        items.add(new Item(150, 1)); // 150
        System.out.println("Total: " + calculate_total(items)); // 618.75
    }
}