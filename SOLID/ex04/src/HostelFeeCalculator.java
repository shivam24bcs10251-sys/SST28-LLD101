import java.util.List;

public class HostelFeeCalculator {

    public double calculateMonthly(List<FeeComponent> components) {

        double total = 0;

        for (FeeComponent component : components) {
            total += component.getMonthlyFee();
        }

        return total;
    }

    public double calculateDeposit(List<FeeComponent> components) {

        double total = 0;

        for (FeeComponent component : components) {
            total += component.getDeposit();
        }

        return total;
    }
}