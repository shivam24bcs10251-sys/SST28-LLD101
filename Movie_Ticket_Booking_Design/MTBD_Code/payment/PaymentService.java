package payment;

import models.Booking;
import models.Payment;

/**
 * Service that orchestrates payment processing using the chosen PaymentGateway.
 *
 * The service holds a reference to a PaymentGateway (interface) and delegates
 * the actual charge to it. The gateway can be swapped at runtime.
 *
 * Requirement 8: Payment gateway abstraction with injectable strategy.
 */
public class PaymentService {

    private PaymentGateway gateway;

    public PaymentService(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    /** Hot-swap the gateway (e.g., fallback from Razorpay → Stripe on failure). */
    public void setGateway(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    /**
     * Create a Payment record for the booking, then process it via the gateway.
     *
     * @param booking The pending booking to pay for
     * @return the Payment object with updated status
     */
    public Payment pay(Booking booking) {
        Payment payment = new Payment(booking, booking.getTotalAmount(), gateway.getGatewayName());
        boolean success = gateway.processPayment(payment);

        if (success) {
            booking.confirm();
        } else {
            booking.cancel();
        }

        return payment;
    }
}
