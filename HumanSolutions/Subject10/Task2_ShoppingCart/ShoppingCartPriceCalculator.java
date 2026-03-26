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

    public static double calculate_total(List<Item> cart) {
        if (cart == null) return 0.0;
        
        double total = 0.0;
        for (Item itm : cart) total += itm.price * itm.quantity;
        
        // Applying 10% discount if over 500
        double base = (total > 500) ? total * 0.9 : total;
        
        // 25% VAT
        double finalPrice = base * 1.25;
        
        return Math.floor(finalPrice * 100 + 0.5) / 100.0;
    }

    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Item(200, 2));
        items.add(new Item(150, 1));
        System.out.println("Total: " + calculate_total(items));
    }
}
