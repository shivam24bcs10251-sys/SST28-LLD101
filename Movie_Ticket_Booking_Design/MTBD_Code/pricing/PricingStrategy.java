package pricing;

import models.Show;
import models.ShowSeat;

/**
 * Strategy interface for computing the price of a ShowSeat.
 *
 * Requirement 7: Prices can change based on strategies like peak-hour
 * and weekend demand.
 *
 * Design Pattern: Strategy Pattern + Decorator Pattern
 *   - Concrete strategies can be chained (decorator) on top of a base price.
 */
public interface PricingStrategy {

    /**
     * Calculate the price for a given ShowSeat in the context of the Show.
     *
     * @param showSeat the seat whose price is being computed
     * @param show     the show context (used for time/date checks)
     * @return calculated price in INR (₹)
     */
    double calculatePrice(ShowSeat showSeat, Show show);
}
