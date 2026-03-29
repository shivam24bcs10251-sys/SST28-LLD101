package models;

import enums.SeatStatus;

import java.time.LocalDateTime;

/**
 * ShowSeat ties a physical Seat to a specific Show and holds its booking state.
 *
 * This is the core entity for:
 *  - Seat availability
 *  - Concurrent lock (status = LOCKED + lockExpiry)
 *  - Seat hold for 8 minutes (Requirement 10)
 */
public class ShowSeat {
    private final Seat          seat;
    private final Show          show;
    private SeatStatus          status;
    private String              lockedByUserId; // which user holds the lock
    private LocalDateTime       lockExpiry;     // null if AVAILABLE or BOOKED

    public ShowSeat(Seat seat, Show show) {
        this.seat   = seat;
        this.show   = show;
        this.status = SeatStatus.AVAILABLE;
    }

    // ── Lock / Release / Book ──────────────────────────────────────────────
    public void lock(String userId, LocalDateTime expiry) {
        this.status         = SeatStatus.LOCKED;
        this.lockedByUserId = userId;
        this.lockExpiry     = expiry;
    }

    public void releaseLock() {
        this.status         = SeatStatus.AVAILABLE;
        this.lockedByUserId = null;
        this.lockExpiry     = null;
    }

    public void book() {
        this.status         = SeatStatus.BOOKED;
        this.lockExpiry     = null;
        // keep lockedByUserId as the final owner
    }

    public boolean isLockExpired() {
        return status == SeatStatus.LOCKED
                && lockExpiry != null
                && LocalDateTime.now().isAfter(lockExpiry);
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public Seat          getSeat()            { return seat; }
    public Show          getShow()            { return show; }
    public SeatStatus    getStatus()          { return status; }
    public String        getLockedByUserId()  { return lockedByUserId; }
    public LocalDateTime getLockExpiry()      { return lockExpiry; }

    @Override
    public String toString() {
        return "ShowSeat{seat=" + seat.getLabel()
                + ", status=" + status
                + (lockedByUserId != null ? ", lockedBy='" + lockedByUserId + "'" : "")
                + "}";
    }
}
