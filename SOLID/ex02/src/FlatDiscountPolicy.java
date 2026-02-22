public class FlatDiscountPolicy implements DiscountPolicy {

    private double discountAmount;

    public FlatDiscountPolicy(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double calculateDiscount(double amount) {
        return discountAmount;
    }
}