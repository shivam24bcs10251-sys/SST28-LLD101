import java.util.List;

public class PricingService {

    public double calculateSubtotal(List<OrderLine> lines) {
        double subtotal = 0;
        for (OrderLine line : lines) {
            subtotal += line.item.price * line.quantity;
        }
        return subtotal;
    }
}