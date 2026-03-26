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
        double sum = 0.0;
        for (Item item : items) sum += (item.price * item.quantity);
        if (sum > 500.0) sum = sum * 0.90;
        double total = sum * 1.25;
        return (double) Math.round(total * 100) / 100;
    }
    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Item(200, 2));
        items.add(new Item(150, 1));
        System.out.println("Total: " + calculate_total(items));
    }
}
