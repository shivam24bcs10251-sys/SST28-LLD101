package pricing;

import models.Show;
import models.ShowSeat;

/**
 * PricingContext holds the active PricingStrategy chain and delegates
 * price computation to it.
 *
 * Usage:
 *   PricingStrategy chain = new WeekendPricingStrategy(
 *                               new PeakHourPricingStrategy(
 *                                   new BasePricingStrategy(200), 1.25), 1.20);
 *   PricingContext ctx = new PricingContext(chain);
 *   double price = ctx.getPrice(showSeat, show);
 */
public class PricingContext {

    private PricingStrategy strategy;

    public PricingContext(PricingStrategy strategy) {
        this.strategy = strategy;
    }

    /** Swap strategy at runtime if needed. */
    public void setStrategy(PricingStrategy strategy) {
        this.strategy = strategy;
    }

    public double getPrice(ShowSeat showSeat, Show show) {
        return strategy.calculatePrice(showSeat, show);
    }
}
