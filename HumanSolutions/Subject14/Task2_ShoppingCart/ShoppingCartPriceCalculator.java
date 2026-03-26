import java.util.List;
import java.util.ArrayList;

public class ShoppingCartPriceCalculator {
    public static class Item {
        public double price;
        public int quantity;
        public Item(double p, int q) { price = p; quantity = q; }
    }
    public static double calculate_total(List<Item> items) {
        if (items == null) return 0.0;
        double total = 0.0;
        for (Item itm : items) total += (itm.price * itm.quantity);
        if (total > 500) total *= 0.9;
        return Math.round(total * 1.25 * 100.0) / 100.0;
    }
    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Item(200, 2));
        items.add(new Item(150, 1));
        System.out.println("Total: " + calculate_total(items));
    }
}
