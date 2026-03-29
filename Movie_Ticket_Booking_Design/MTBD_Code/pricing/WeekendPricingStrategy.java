package pricing;

import models.Show;
import models.ShowSeat;

import java.time.DayOfWeek;

/**
 * Weekend Demand Pricing Decorator.
 *
 * Wraps another PricingStrategy and applies a demand multiplier on
 * Saturdays and Sundays.
 *
 * Requirement 7: Weekend demand strategy.
 */
public class WeekendPricingStrategy implements PricingStrategy {

    private final PricingStrategy innerStrategy;
    private final double          demandMultiplier;  // e.g. 1.20 for +20%

    public WeekendPricingStrategy(PricingStrategy innerStrategy, double demandMultiplier) {
        this.innerStrategy    = innerStrategy;
        this.demandMultiplier = demandMultiplier;
    }

    @Override
    public double calculatePrice(ShowSeat showSeat, Show show) {
        double    price     = innerStrategy.calculatePrice(showSeat, show);
        DayOfWeek day       = show.getStartTime().getDayOfWeek();
        boolean   isWeekend = day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;

        if (isWeekend) {
            System.out.printf("    [Weekend] demand multiplier applied (x%.2f) on seat %s%n",
                    demandMultiplier, showSeat.getSeat().getLabel());
            price *= demandMultiplier;
        }
        return price;
    }
}
