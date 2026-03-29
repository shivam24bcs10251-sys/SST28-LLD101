package payment;

import models.Payment;

import java.util.UUID;

/**
 * Simulated Stripe Payment Gateway.
 *
 * Alternative gateway — same interface as Razorpay.
 * Can be swapped in at runtime without changing BookingService.
 *
 * Requirement 8: Multiple payment gateway implementations.
 */
public class StripeGateway implements PaymentGateway {

    @Override
    public boolean processPayment(Payment payment) {
        System.out.println("  [Stripe] Initiating payment of ₹" + payment.getAmount()
                + " for booking " + payment.getBooking().getId() + " ...");

        // Simulate API call (always succeeds in demo)
        boolean success = true;
        if (success) {
            String txnRef = "STR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            payment.markSuccess(txnRef);
            System.out.println("  [Stripe] Payment SUCCESS. TxnRef: " + txnRef);
        } else {
            payment.markFailed();
            System.out.println("  [Stripe] Payment FAILED.");
        }
        return success;
    }

    @Override
    public String getGatewayName() { return "Stripe"; }
}
