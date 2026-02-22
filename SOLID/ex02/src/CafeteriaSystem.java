import java.util.List;

public class CafeteriaSystem {

    private PricingService pricingService;
    private TaxPolicy taxPolicy;
    private DiscountPolicy discountPolicy;
    private InvoiceFormatter formatter;
    private InvoiceRepository repository;

    public CafeteriaSystem(PricingService pricingService,
                           TaxPolicy taxPolicy,
                           DiscountPolicy discountPolicy,
                           InvoiceFormatter formatter,
                           InvoiceRepository repository) {

        this.pricingService = pricingService;
        this.taxPolicy = taxPolicy;
        this.discountPolicy = discountPolicy;
        this.formatter = formatter;
        this.repository = repository;
    }

    public void checkout(String invoiceId, List<OrderLine> lines) {

        double subtotal = pricingService.calculateSubtotal(lines);
        double tax = taxPolicy.calculateTax(subtotal);
        double discount = discountPolicy.calculateDiscount(subtotal);
        double total = subtotal + tax - discount;

        String invoiceText = formatter.format(
                invoiceId, lines, subtotal, tax, discount, total);

        System.out.println(invoiceText);
        repository.save(invoiceId, invoiceText);
    }
}