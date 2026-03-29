package models;

import enums.PaymentStatus;

import java.util.UUID;

/**
 * Represents a single payment transaction linked to a Booking.
 */
public class Payment {
    private final String        id;
    private final Booking       booking;
    private final double        amount;
    private final String        gatewayName;  // "Razorpay", "Stripe", etc.
    private PaymentStatus       status;
    private String              transactionRef;

    public Payment(Booking booking, double amount, String gatewayName) {
        this.id          = "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.booking     = booking;
        this.amount      = amount;
        this.gatewayName = gatewayName;
        this.status      = PaymentStatus.PENDING;
    }

    public void markSuccess(String transactionRef) {
        this.status         = PaymentStatus.SUCCESS;
        this.transactionRef = transactionRef;
    }

    public void markFailed() {
        this.status = PaymentStatus.FAILED;
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String        getId()             { return id; }
    public Booking       getBooking()        { return booking; }
    public double        getAmount()         { return amount; }
    public String        getGatewayName()    { return gatewayName; }
    public PaymentStatus getStatus()         { return status; }
    public String        getTransactionRef() { return transactionRef; }

    @Override
    public String toString() {
        return "Payment{id='" + id + "', gateway='" + gatewayName
                + "', amount=₹" + amount + "', status=" + status + "}";
    }
}
