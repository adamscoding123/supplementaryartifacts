import java.util.List;
import java.util.ArrayList;

public class ShoppingCartPriceCalculator {
    public static class Item {
        public double price;
        public int quantity;
        public Item(double price, int quantity) {
            this.price = price;
            this.quantity = quantity;
        }
    }
    public static double calculate_total(final List<Item> items) {
        if (items == null) {
            return 0.0;
        }
        double totalSum = 0.0;
        for (final Item item : items) {
            totalSum += item.price * (double) item.quantity;
        }
        if (totalSum > 500.0) {
            totalSum *= 0.90;
        }
        final double finalValueWithVat = totalSum * 1.25;
        return (double) Math.round(finalValueWithVat * 100.0) / 100.0;
    }
    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Item(200, 2));
        items.add(new Item(150, 1));
        System.out.println("Total: " + calculate_total(items));
    }
}
