public class ReceiptFormatter {

    public String format(BookingRequest request,
                         double monthly,
                         double deposit,
                         double total) {

        StringBuilder sb = new StringBuilder();

        sb.append("=== Hostel Fee Calculator ===\n");
        sb.append("Room: ")
          .append(request.roomType)
          .append(" | AddOns: ")
          .append(request.addOns)
          .append("\n");

        sb.append("Monthly: ")
          .append(String.format("%.2f", monthly))
          .append("\n");

        sb.append("Deposit: ")
          .append(String.format("%.2f", deposit))
          .append("\n");

        sb.append("TOTAL DUE NOW: ")
          .append(String.format("%.2f", total));

        return sb.toString();
    }
}