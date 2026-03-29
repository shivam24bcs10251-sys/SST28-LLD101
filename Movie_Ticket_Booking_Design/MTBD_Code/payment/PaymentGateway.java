package payment;

import models.Payment;

/**
 * Payment Gateway Interface (Requirement 8).
 *
 * Any real-world payment processor (Razorpay, Stripe, PayU, etc.) must
 * implement this interface, ensuring the BookingService is decoupled
 * from any concrete gateway implementation.
 *
 * Design Pattern: Strategy Pattern for payment gateways.
 */
public interface PaymentGateway {

    /**
     * Process the payment.
     *
     * @param payment the Payment object containing amount and booking details
     * @return true if the payment was successful, false otherwise
     */
    boolean processPayment(Payment payment);

    /**
     * @return the human-readable name of this gateway
     */
    String getGatewayName();
}
