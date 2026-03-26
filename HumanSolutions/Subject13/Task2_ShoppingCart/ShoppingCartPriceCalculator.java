import java.util.List;
import java.util.ArrayList;

public class ShoppingCartPriceCalculator {
    public static class Item {
        public double price;
        public int quantity;
        public Item(double p, int q) { this.price = p; this.quantity = q; }
    }
    public static double calculate_total(List<Item> items) {
        if (items == null) return 0.0;
        double subtotal = calculateSubtotal(items);
        double afterDiscount = applyDiscount(subtotal);
        double withVat = addTax(afterDiscount);
        return roundToTwoDecimals(withVat);
    }
    private static double calculateSubtotal(List<Item> list) {
        double s = 0;
        for (Item i : list) s += i.price * i.quantity;
        return s;
    }
    private static double applyDiscount(double val) { return val > 500 ? val * 0.9 : val; }
    private static double addTax(double val) { return val * 1.25; }
    private static double roundToTwoDecimals(double val) { return Math.round(val * 100.0) / 100.0; }
    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Item(200, 2));
        items.add(new Item(150, 1));
        System.out.println("Total: " + calculate_total(items));
    }
}
