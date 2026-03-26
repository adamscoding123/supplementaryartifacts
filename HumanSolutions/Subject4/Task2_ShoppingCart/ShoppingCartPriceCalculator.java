import java.util.List;

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
        for (Item itm : items) {
            subtotal += (itm.price * itm.quantity);
        }
        
        // Discount
        if (subtotal > 500.0) {
            subtotal = subtotal * 0.90;
        }
        
        // VAT (25%)
        double finalTotal = subtotal * 1.25;
        
        // Round to 2 decimal places
        return Math.round(finalTotal * 100.0) / 100.0;
    }

    public static void main(String[] args) {
        java.util.List<Item> items = new java.util.ArrayList<>();
        items.add(new Item(200, 2)); // 400
        items.add(new Item(150, 1)); // 150
        // Total = 550. > 500, so 10% discount = 495.
        // +25% VAT = 618.75.
        System.out.println("Total: " + calculate_total(items));
    }
}
