package models;

import enums.SeatType;

/**
 * Represents a physical seat in a Screen.
 *
 * A Seat has a SeatType (SILVER/GOLD/PLATINUM) which determines its base
 * price multiplier. Row and number together form the human-readable label
 * like "A12" or "C5".
 *
 * Requirement: Each screen can have different seats with different prices.
 */
public class Seat {
    private final String   id;
    private final char     row;     // 'A', 'B', 'C' ...
    private final int      number;  // 1, 2, 3 ...
    private final SeatType type;

    public Seat(String id, char row, int number, SeatType type) {
        this.id     = id;
        this.row    = row;
        this.number = number;
        this.type   = type;
    }

    public String   getId()     { return id; }
    public char     getRow()    { return row; }
    public int      getNumber() { return number; }
    public SeatType getType()   { return type; }

    public String getLabel() { return "" + row + number; }

    @Override
    public String toString() {
        return "Seat{" + getLabel() + ", type=" + type + "}";
    }
}
