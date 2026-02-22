import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<String> addOns = new ArrayList<>();
        addOns.add("LAUNDRY");
        addOns.add("MESS");

        BookingRequest request =
                new BookingRequest("H-7781", "DOUBLE", addOns);

        List<FeeComponent> components = new ArrayList<>();

        // Room mapping (no switch in calculator!)
        if (request.roomType.equals("SINGLE")) {
            components.add(new SingleRoom());
        } else if (request.roomType.equals("DOUBLE")) {
            components.add(new DoubleRoom());
        }

        for (String addon : request.addOns) {
            if (addon.equals("LAUNDRY")) {
                components.add(new LaundryAddOn());
            } else if (addon.equals("MESS")) {
                components.add(new MessAddOn());
            }
        }

        HostelFeeCalculator calculator = new HostelFeeCalculator();

        double monthly = calculator.calculateMonthly(components);
        double deposit = calculator.calculateDeposit(components);
        double total = monthly + deposit;

        ReceiptFormatter formatter = new ReceiptFormatter();
        String receipt =
                formatter.format(request, monthly, deposit, total);

        System.out.println(receipt);

        BookingRepository repo = new InMemoryBookingRepository();
        repo.save(request);
    }
}