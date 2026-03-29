package pricing;

import models.Show;
import models.ShowSeat;

/**
 * Peak Hour Pricing Decorator.
 *
 * Wraps another PricingStrategy and applies a surge multiplier if the show
 * starts during peak hours (18:00 – 22:00).
 *
 * Requirement 7: Peak hour strategy.
 *
 * Design Pattern: Decorator — delegates to inner strategy and then applies surge.
 */
public class PeakHourPricingStrategy implements PricingStrategy {

    private final PricingStrategy innerStrategy;
    private final double          surgeMultiplier;  // e.g. 1.25 for +25%
    private static final int      PEAK_START = 18;  // 6 PM
    private static final int      PEAK_END   = 22;  // 10 PM

    public PeakHourPricingStrategy(PricingStrategy innerStrategy, double surgeMultiplier) {
        this.innerStrategy   = innerStrategy;
        this.surgeMultiplier = surgeMultiplier;
    }

    @Override
    public double calculatePrice(ShowSeat showSeat, Show show) {
        double price = innerStrategy.calculatePrice(showSeat, show);
        int    hour  = show.getStartTime().getHour();

        if (hour >= PEAK_START && hour < PEAK_END) {
            System.out.printf("    [PeakHour] %s surge applied (x%.2f) on seat %s%n",
                    surgeMultiplier, surgeMultiplier, showSeat.getSeat().getLabel());
            price *= surgeMultiplier;
        }
        return price;
    }
}
