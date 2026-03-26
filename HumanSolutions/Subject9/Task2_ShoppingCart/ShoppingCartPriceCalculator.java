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
        
        double subtotal = 0.0;
        for (Item item : items) {
            subtotal += calculateItemTotal(item);
        }
        
        double discounted = applyDiscount(subtotal);
        double totalWithTax = addVat(discounted);
        
        return Math.round(totalWithTax * 100.0) / 100.0;
    }
    
    private static double calculateItemTotal(Item i) { return i.price * i.quantity; }
    private static double applyDiscount(double val) { return val > 500 ? val * 0.9 : val; }
    private static double addVat(double val) { return val * 1.25; }

    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Item(200, 2));
        items.add(new Item(150, 1));
        System.out.println("Total: " + calculate_total(items));
    }
}
