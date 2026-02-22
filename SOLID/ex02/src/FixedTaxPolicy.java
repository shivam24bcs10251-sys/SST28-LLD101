public class FixedTaxPolicy implements TaxPolicy {

    private double rate;

    public FixedTaxPolicy(double rate) {
        this.rate = rate;
    }

    public double calculateTax(double amount) {
        return amount * rate;
    }
}