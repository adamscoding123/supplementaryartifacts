import java.util.List;
import java.util.ArrayList;

public class ShoppingCartPriceCalculator {
    public static class Item {
        public double price;
        public int quantity;
        public Item(double price, int quantity) { this.price = price; this.quantity = quantity; }
    }

    public static double calculate_total(List<Item> items) {
        if (items == null) return 0.0;
        double sum = items.stream().mapToDouble(item -> item.price * item.quantity).sum();
        double discount = sum > 500 ? 0.90 : 1.0;
        double withVat = sum * discount * 1.25;
        return Double.parseDouble(String.format(java.util.Locale.US, "%.2f", withVat));
    }

    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Item(200, 2)); // 400
        items.add(new Item(150, 1)); // 150
        System.out.println("Total: " + calculate_total(items)); // 618.75
    }
}