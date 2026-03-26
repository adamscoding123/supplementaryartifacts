import java.util.List;
public class ShoppingCartPriceCalculator {
    public static class Item {
        public double price;
        public int qty;
        public Item(double price, int qty) { this.price = price; this.qty = qty; }
    }
    public double calculateTotal(List<Item> items) {
        if (items == null || items.isEmpty()) return 0.00;
        double subtotal = 0;
        for (Item item : items) {
            subtotal += item.price * item.qty;
        }
        double afterDiscount = subtotal > 500 ? subtotal * 0.90 : subtotal;
        double withVat = afterDiscount * 1.25;
        return Math.round(withVat * 100.0) / 100.0;
    }

    public static void main(String[] args) {
        ShoppingCartPriceCalculator calc = new ShoppingCartPriceCalculator();
        System.out.println("Basic (100x2) -> " + calc.calculateTotal(java.util.Arrays.asList(new Item(100, 2)))); // 250.0
        System.out.println("Discount (300x2) -> " + calc.calculateTotal(java.util.Arrays.asList(new Item(300, 2)))); // 675.0
        System.out.println("Boundary (250x2) -> " + calc.calculateTotal(java.util.Arrays.asList(new Item(250, 2)))); // 625.0
        System.out.println("Rounding -> " + calc.calculateTotal(java.util.Arrays.asList(new Item(250.5, 2), new Item(0.5, 1)))); // 564.19
        System.out.println("Empty -> " + calc.calculateTotal(new java.util.ArrayList<>())); // 0.0
    }
}
