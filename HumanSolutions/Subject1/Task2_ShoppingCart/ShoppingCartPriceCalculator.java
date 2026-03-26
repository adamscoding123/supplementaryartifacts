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

    public static double calculate_total(List<Item> items) {
        if (items == null) return 0.0;
        double total = 0;
        for (Item item : items) {
            total += item.price * item.quantity;
        }
        if (total > 500.0) {
            total *= 0.90; // 10% discount
        }
        total *= 1.25; // 25% VAT
        return Math.round(total * 100.0) / 100.0;
    }

    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Item(200, 2)); // 400
        items.add(new Item(150, 1)); // 150
        // Total = 550. > 500, so 10% discount = 495.
        // +25% VAT = 618.75.
        System.out.println("Total: " + calculate_total(items));
    }
}
