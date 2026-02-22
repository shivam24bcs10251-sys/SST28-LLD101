import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== Cafeteria Billing ===");

        MenuItem vegThali = new MenuItem("Veg Thali", 80.00);
        MenuItem coffee = new MenuItem("Coffee", 30.00);

        List<OrderLine> order = new ArrayList<>();
        order.add(new OrderLine(vegThali, 2));
        order.add(new OrderLine(coffee, 1));

        PricingService pricing = new PricingService();
        TaxPolicy tax = new FixedTaxPolicy(0.05);
        DiscountPolicy discount = new FlatDiscountPolicy(10.00);
        InvoiceFormatter formatter = new InvoiceFormatter();
        InvoiceRepository repo = new InMemoryInvoiceRepository();

        CafeteriaSystem system = new CafeteriaSystem(
                pricing, tax, discount, formatter, repo
        );

        system.checkout("INV-1001", order);
    }
}