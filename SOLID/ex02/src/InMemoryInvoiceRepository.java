import java.util.HashMap;

public class InMemoryInvoiceRepository implements InvoiceRepository {

    private HashMap<String, String> store = new HashMap<>();

    public void save(String invoiceId, String content) {
        store.put(invoiceId, content);
        System.out.println("Saved invoice: " + invoiceId + " (lines=" + content.split("\n").length + ")");
    }
}