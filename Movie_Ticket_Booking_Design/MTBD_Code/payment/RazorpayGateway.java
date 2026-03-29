package payment;

import models.Payment;

import java.util.UUID;

/**
 * Simulated Razorpay Payment Gateway.
 *
 * In production this would call Razorpay REST APIs.
 * For the demo, we simulate a successful payment 90% of the time.
 *
 * Requirement 8: Payment gateway interface with concrete implementation.
 */
public class RazorpayGateway implements PaymentGateway {

    @Override
    public boolean processPayment(Payment payment) {
        System.out.println("  [Razorpay] Initiating payment of ₹" + payment.getAmount()
                + " for booking " + payment.getBooking().getId() + " ...");

        // Simulate API call (always succeeds in demo)
        boolean success = true;  // change to Math.random() > 0.1 for realistic simulation
        if (success) {
            String txnRef = "RZP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            payment.markSuccess(txnRef);
            System.out.println("  [Razorpay] Payment SUCCESS. TxnRef: " + txnRef);
        } else {
            payment.markFailed();
            System.out.println("  [Razorpay] Payment FAILED.");
        }
        return success;
    }

    @Override
    public String getGatewayName() { return "Razorpay"; }
}
