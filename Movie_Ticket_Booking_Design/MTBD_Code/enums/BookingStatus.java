package enums;

/**
 * Overall status of a Booking record.
 *
 * PENDING    → seats locked, awaiting payment
 * CONFIRMED  → payment succeeded
 * CANCELLED  → booking was cancelled (lock expired or user cancelled)
 */
public enum BookingStatus {
    PENDING,
    CONFIRMED,
    CANCELLED
}
