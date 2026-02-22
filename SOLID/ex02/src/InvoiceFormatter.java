import java.util.List;

public class InvoiceFormatter {

    public String format(String invoiceId,
                         List<OrderLine> lines,
                         double subtotal,
                         double tax,
                         double discount,
                         double total) {

        StringBuilder sb = new StringBuilder();

        sb.append("Invoice# ").append(invoiceId).append("\n");

        for (OrderLine line : lines) {
            double lineTotal = line.item.price * line.quantity;
            sb.append("- ")
              .append(line.item.name)
              .append(" x")
              .append(line.quantity)
              .append(" = ")
              .append(String.format("%.2f", lineTotal))
              .append("\n");
        }

        sb.append("Subtotal: ").append(String.format("%.2f", subtotal)).append("\n");
        sb.append("Tax(5%): ").append(String.format("%.2f", tax)).append("\n");
        sb.append("Discount: -").append(String.format("%.2f", discount)).append("\n");
        sb.append("TOTAL: ").append(String.format("%.2f", total));

        return sb.toString();
    }
}