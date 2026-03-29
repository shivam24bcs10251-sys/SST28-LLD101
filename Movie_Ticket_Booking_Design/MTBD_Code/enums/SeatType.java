package enums;

/**
 * Represents the category/tier of a seat in a screen.
 * Each type carries a different base price multiplier.
 */
public enum SeatType {
    SILVER(1.0),    // economy seats (lower rows)
    GOLD(1.5),      // mid-tier seats
    PLATINUM(2.0);  // premium seats (best view)

    private final double priceMultiplier;

    SeatType(double priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public double getPriceMultiplier() {
        return priceMultiplier;
    }
}
