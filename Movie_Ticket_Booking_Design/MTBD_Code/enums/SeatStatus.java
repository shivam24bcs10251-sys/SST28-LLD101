package enums;

/**
 * Lifecycle status of a ShowSeat (seat within a specific show).
 *
 * AVAILABLE  → seat is open for selection
 * LOCKED     → seat is temporarily held (max 8 minutes) while user pays
 * BOOKED     → payment confirmed; seat is permanently reserved
 */
public enum SeatStatus {
    AVAILABLE,
    LOCKED,
    BOOKED
}
