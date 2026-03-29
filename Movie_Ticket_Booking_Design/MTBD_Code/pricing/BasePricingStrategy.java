package pricing;

import models.Show;
import models.ShowSeat;

/**
 * Base pricing strategy.
 *
 * Applies the SeatType multiplier to a configurable base price.
 * e.g., SILVER = 200 * 1.0 = ₹200, GOLD = 200 * 1.5 = ₹300, PLATINUM = 200 * 2.0 = ₹400
 *
 * Requirement 6: Prices are different for different seat types.
 */
public class BasePricingStrategy implements PricingStrategy {

    private final double basePrice;

    public BasePricingStrategy(double basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public double calculatePrice(ShowSeat showSeat, Show show) {
        return basePrice * showSeat.getSeat().getType().getPriceMultiplier();
    }
}
