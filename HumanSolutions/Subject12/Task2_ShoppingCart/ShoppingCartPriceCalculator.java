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
        double sum = items.stream().mapToDouble(i -> i.price * i.quantity).sum();
        double discounted = (sum > 500) ? sum * 0.9 : sum;
        double total = discounted * 1.25;
        return Math.round(total * 100.0) / 100.0;
    }
    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Item(200, 2));
        items.add(new Item(150, 1));
        System.out.println("Total: " + calculate_total(items));
    }
}
