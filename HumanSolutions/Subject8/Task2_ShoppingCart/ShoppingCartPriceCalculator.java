import java.util.List;
import java.util.ArrayList;

public class ShoppingCartPriceCalculator {
    public static class Item {
        public double price;
        public int quantity;
        public Item(double p, int q) { price = p; quantity = q; }
    }

    public static double calculate_total(List<Item> list) {
        if (list == null) return 0.0;
        double sum = 0;
        for (Item i : list) sum += (i.price * i.quantity);
        if (sum > 500) sum *= 0.9;
        return Math.floor(sum * 1.25 * 100 + 0.5) / 100;
    }

    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Item(200, 2));
        items.add(new Item(150, 1));
        System.out.println("Total: " + calculate_total(items));
    }
}
